/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.fxb.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fxb.jeesite.common.persistence.Page;
import com.fxb.jeesite.common.service.CrudService;
import com.fxb.jeesite.modules.oa.entity.Qr;
import com.fxb.jeesite.modules.oa.dao.QrDao;

/**
 * 二维码Service
 *
 * @author fxb
 * @version 2017-07-24
 */
@Service
@Transactional(readOnly = true)
public class QrService extends CrudService<QrDao, Qr> {

    public Qr get(String id) {
        return super.get(id);
    }

    public List<Qr> findList(Qr qr) {
        return super.findList(qr);
    }

    public Page<Qr> findPage(Page<Qr> page, Qr qr) {
        return super.findPage(page, qr);
    }

    @Transactional(readOnly = false)
    public void save(Qr qr) {
        super.save(qr);
    }

    @Transactional(readOnly = false)
    public void delete(Qr qr) {
        super.delete(qr);
    }

    @Transactional
    public void update(Qr qr) {
        dao.update(qr);
    }
}