package com.dauivs.storeassistant.controller.sale;

import com.dauivs.storeassistant.common.ResponseData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.sale.CustomerDao;
import com.dauivs.storeassistant.model.sale.Customer;
import com.dauivs.storeassistant.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/sale/customer")
public class CustomerController {

    @Autowired
    private CustomerDao dao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseData info(@PathVariable int id) {
        return ResponseData.success(dao.findById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseData delete(@PathVariable int id) {
        return ResponseData.success(CommonUtil.delete(dao, id));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseData save(@RequestBody Customer customer) {
        Customer sameTel = dao.findByTel(customer.getTel());
        if (sameTel != null && !Objects.equals(sameTel.getId(), customer.getId())) {
            return ResponseData.fail("电话号码已存在");
        }
        return ResponseData.success(CommonUtil.save(dao, customer));
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseData page(@RequestBody SearchParameter searchParameter) {
        return ResponseData.success(dao.findPage(searchParameter));
    }

    @RequestMapping(value = "/tel/{tel}", method = RequestMethod.GET)
    public ResponseData findByTel(@PathVariable String tel) {
        return ResponseData.success(dao.findByTel(tel));
    }
}
