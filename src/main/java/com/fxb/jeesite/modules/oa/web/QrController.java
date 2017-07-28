/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.fxb.jeesite.modules.oa.web;

import com.fxb.jeesite.common.config.Global;
import com.fxb.jeesite.common.persistence.Page;
import com.fxb.jeesite.common.qr.QRCodeUtil;
import com.fxb.jeesite.common.utils.FileUtils;
import com.fxb.jeesite.common.utils.StringUtils;
import com.fxb.jeesite.common.web.BaseController;
import com.fxb.jeesite.modules.oa.entity.Qr;
import com.fxb.jeesite.modules.oa.service.QrService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 二维码Controller
 *
 * @author 方小白
 * @version 2017-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/qr")
public class QrController extends BaseController {

    @Autowired
    private QrService qrService;

    @ModelAttribute
    public Qr get(@RequestParam(required = false) String id) {
        Qr entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = qrService.get(id);
        }
        if (entity == null) {
            entity = new Qr();
        }
        return entity;
    }

    @RequiresPermissions("oa:qr:view")
    @RequestMapping(value = {"list", ""})
    public String list(Qr qr, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Qr> page = qrService.findPage(new Page<Qr>(request, response), qr);
        model.addAttribute("page", page);
        return "modules/oa/qrList";
    }

    @RequiresPermissions("oa:qr:view")
    @RequestMapping(value = "form")
    public String form(Qr qr, Model model) {
        model.addAttribute("qr", qr);
        return "modules/oa/qrForm";
    }

    @RequiresPermissions("oa:qr:edit")
    @RequestMapping(value = "save")
    public String save(Qr qr, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, qr)) {
            return form(qr, model);
        }
        qrService.save(qr);
        addMessage(redirectAttributes, "保存二维码成功");
        return "redirect:" + Global.getAdminPath() + "/oa/qr/?repage";
    }

    @RequiresPermissions("oa:qr:edit")
    @RequestMapping(value = "delete")
    public String delete(Qr qr, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        //从磁盘删除文件：
        qr = qrService.get(qr.getId());
        String rootPath = Global.getUserfilesBaseDir();
        //删除../imgs/..jpg文件;
        //删除指定的文件
        String imgsFilePath = qr.getFilepath();
        FileUtils.deleteDirectory(imgsFilePath);

        //删除 ../zips/...zip文件；
        String zipFilePath = rootPath + File.separator + qr.getFilename();
        FileUtils.deleteFile(zipFilePath);
        qrService.delete(qr);
        addMessage(redirectAttributes, "删除二维码成功");
        return "redirect:" + Global.getAdminPath() + "/oa/qr/?repage";
    }

    @RequiresPermissions("oa:qr:view")
    @RequestMapping("creatQr")
    public String createQr(String startid, String endid, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (StringUtils.isNotBlank(startid) && StringUtils.isNotBlank(endid)) {
            // 生成二维码
            try {
                String webRootPath = Global.getUserfilesBaseDir();
                String logoPath = Global.getQrLogo();
                QRCodeUtil.setLogoPath(webRootPath + File.separator + logoPath);

                int qRCODE_HEIGHT = Integer.parseInt(Global.getQrHeight());
                QRCodeUtil.setQRCODE_HEIGHT(qRCODE_HEIGHT);
                int qRCODE_WIDTH = Integer.parseInt(Global.getQrWidth());
                QRCodeUtil.setQRCODE_WIDTH(qRCODE_WIDTH);

                // set qr create path
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String rootPath = "qr" + File.separator + year + "-" + month + "-" + day + File.separator + "imgs";
                //生成二维码的路径问题：
                //当同一天生成n次，那么生成的相同的id就会覆盖。删除的时候就会删除同一张。
                //解决方式：每次生成二维码都放在不同的文件夹中，
                File file = new File(rootPath + 0);
                int pathCounts = 0;
                String filePath = null;
                while (file.exists()) {
                    filePath = rootPath + ++pathCounts;
                    file = new File(webRootPath + File.separator + filePath);
                }
                QRCodeUtil.setRootPath(file);
                String URL = Global.getQrUrl();
                QRCodeUtil.setURL(URL);
                List<String> errors = QRCodeUtil.createQR(Integer.parseInt(startid), Integer.parseInt(endid));
                // 向数据库中插入数据
                Qr qr = new Qr();
                qr.setEndid(endid);
                qr.setStartid(startid);
                //filePath地址是： xxx/imgsN;
                qr.setFilepath(filePath);
                qr.setName(year + "-" + month + "-" + day + "_" + startid + "-" + endid);
                qr.setTime(new Date());
                this.qrService.save(qr);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "redirect:" + Global.getAdminPath() + "/oa/qr/?repage";
    }


    @RequestMapping("zipAndDownload")
    @RequiresPermissions("oa:qr:edit")
    @ResponseBody
    public void zipAndDownload(Qr qr, Model model, HttpServletRequest request, HttpServletResponse response) {
        Qr findQr = this.get(qr.getId());
        String webPath = Global.getUserfilesBaseDir();
        try {
            if (findQr != null) {
                String srcPath =  qr.getFilepath();
                String aimPath = srcPath.substring(0, srcPath.length() - 6) + File.separator + "zips";
                String fileName = qr.getName() + ".zip";
                if (findQr.getFilename() == null) {
                    //文件尚未被压缩
                    File aimFilePath = new File(webPath+File.separator+aimPath);
                    if (!aimFilePath.exists()) {
                        aimFilePath.mkdirs();
                    }

                    FileUtils.zipFiles(webPath + File.separator +srcPath, "", webPath + File.separator +aimPath + File.separator + fileName);
                    //设置fileName
                    qr.setFilename(aimPath + File.separator + fileName);
                    this.qrService.update(qr);
                }
                Thread.sleep(1000);
                String downloadPath = webPath + File.separator + aimPath + File.separator + fileName;

                FileUtils.downFile(new File(downloadPath), request, response, fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}