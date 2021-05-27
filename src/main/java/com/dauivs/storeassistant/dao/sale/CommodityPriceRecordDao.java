package com.dauivs.storeassistant.dao.sale;

import com.dauivs.storeassistant.model.sale.CommodityPriceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommodityPriceRecordDao extends JpaRepository<CommodityPriceRecord, Integer>, CommodityPriceRecordDaoCustom {
}

interface CommodityPriceRecordDaoCustom {

}

class CommodityPriceRecordDaoCustomImpl implements CommodityPriceRecordDaoCustom {

}
