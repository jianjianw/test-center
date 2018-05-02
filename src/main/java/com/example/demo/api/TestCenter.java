package com.example.demo.api;


import com.example.demo.util.NhhosUtil;
import com.nhsoft.module.base.export.dto.AssembleDetailDTO;
import com.nhsoft.module.base.export.dto.BranchItemKitQuery;
import com.nhsoft.module.base.export.dto.OrderQueryCondition;
import com.nhsoft.module.base.export.rpc.BranchItemKitRpc;
import com.nhsoft.module.base.export.rpc.BranchRpc;
import com.nhsoft.module.pos.export.dto.PolicyAllowPriftQuery;
import com.nhsoft.module.pos.export.rpc.PosOrderRpc;
import com.nhsoft.module.transfer.export.dto.*;
import com.nhsoft.module.transfer.export.rpc.PurchaseOrderRpc;
import com.nhsoft.module.transfer.export.rpc.ReceiveOrderRpc;
import com.nhsoft.module.transfer.export.rpc.SettlementRpc;
import com.nhsoft.module.transfer.export.rpc.SupplierUserRpc;
import com.nhsoft.module.wholesale.export.dto.PreSettlementQueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "testCenter")
public class TestCenter {

    @Autowired
    private NhhosUtil nhhosUtil;

    private String systemBookCode = "4020";

    @RequestMapping(method = RequestMethod.GET,value = "/test1/{branchNum}")
    public List<AssembleDetailDTO> test1(@PathVariable(value = "branchNum") Integer branchNum){


        String url = nhhosUtil.getUrl(systemBookCode);
        BranchItemKitRpc centerObject = nhhosUtil.createCenterObject(BranchItemKitRpc.class, url);

        BranchItemKitQuery query = new BranchItemKitQuery();
        query.setBranchNum(branchNum);
        query.setSystemBookCode("4020");
        List<AssembleDetailDTO> assembleDetailByBranch = centerObject.findAssembleDetailByBranch(query);
        return assembleDetailByBranch;
    }



    @RequestMapping(method = RequestMethod.GET,value = "/test2")
    public List<Object[]> test2() throws Exception{


        String url = nhhosUtil.getUrl(systemBookCode);
        PosOrderRpc posOrderRpc = nhhosUtil.createCenterObject(PosOrderRpc.class, url);



        String systemBookCode = "4020";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = sdf.parse("2018-04-01");
        Date dateTo = sdf.parse("2018-04-18");

        PolicyAllowPriftQuery query = new PolicyAllowPriftQuery();

        query.setSystemBookCode(systemBookCode);
        query.setDtFrom(dateFrom);
        query.setDtTo(dateTo);
        List<Integer> branchNums = new ArrayList<>();
        branchNums.add(99);
        query.setBranchNums(branchNums);
        query.setPromotion(true);
        List<Object[]> promotionItems = posOrderRpc.findPromotionItems(systemBookCode,query);
        return promotionItems;
    }


    @RequestMapping(method = RequestMethod.GET,value = "/test3")
    public SupplierUserDTO test3() throws Exception{


        String url = nhhosUtil.getUrl(systemBookCode);


       // PosOrderRpc posOrderRpc = nhhosUtil.createCenterObject(PosOrderRpc.class, url);
        SupplierUserRpc supplierUserRpc = nhhosUtil.createCenterObject(SupplierUserRpc.class, url);


        String systemBookCode = "4020";
        String code = "0011";
        String password = "nhsoft123";

        SupplierUserDTO login = supplierUserRpc.login(systemBookCode, code, password);
        return login;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/test4")
    public PurchaseOrderPageDTO test4() throws Exception{


        String url = nhhosUtil.getUrl(systemBookCode);
        PurchaseOrderQuery query = new PurchaseOrderQuery();
        String systemBookCode = "4344";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = sdf.parse("2018-04-01");
        Date dateTo = sdf.parse("2018-04-30");
        List<Integer> branchNums = new ArrayList<>();
        branchNums.add(99);
        query.setSystemBookCode(systemBookCode);
        //query.setBranchNums(branchNums);
        query.setDateFrom(dateFrom);
        query.setDateTo(dateTo);
        query.setPage(true);
        query.setIsNew(false);
        query.setSupplierNum(434499048);
        PurchaseOrderRpc purchaseOrderRpc = nhhosUtil.createCenterObject(PurchaseOrderRpc.class, url);
        PurchaseOrderPageDTO purchaseOrderByPage = purchaseOrderRpc.findPurchaseOrderByPage(systemBookCode,query,0,20);

        return purchaseOrderByPage;

    }

    @RequestMapping(method = RequestMethod.GET,value = "/test4_1")
    public PurchaseOrderDTO test4_1() throws Exception{


        String url = nhhosUtil.getUrl(systemBookCode);
        String orderFid = "PO4020990000111";
        PurchaseOrderRpc purchaseOrderRpc = nhhosUtil.createCenterObject(PurchaseOrderRpc.class, url);
        PurchaseOrderDTO read = purchaseOrderRpc.read(systemBookCode, orderFid);
        return read;

    }




    @RequestMapping(method = RequestMethod.GET,value = "/test5")
    public List<ReceiveReturnOrderDTO> test5() throws Exception{
        String url = nhhosUtil.getUrl(systemBookCode);

        OrderQueryCondition query = new OrderQueryCondition();
        String systemBookCode = "4344";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = sdf.parse("2018-04-25");
        Date dateTo = sdf.parse("2018-04-25");
        List<Integer> branchNums = new ArrayList<>();
        branchNums.add(99);
        query.setSystemBookCode(systemBookCode);
        query.setToBranchNums(branchNums);
        query.setDateStart(dateFrom);
        query.setDateEnd(dateTo);
        //query.setFid("PI4344990001505");
        //query.setOrderState("收货单");
        query.setSupplierNum(434499048);
        ReceiveOrderRpc receiveOrderRpc = nhhosUtil.createCenterObject(ReceiveOrderRpc.class, url);
        List<ReceiveReturnOrderDTO> result = receiveOrderRpc.findReceiveReturnOrderByQuery(systemBookCode, query);

        return result;

    }

    @RequestMapping(method = RequestMethod.GET,value = "/test6")
    public ReceiveReturnOrderDTO test6() throws Exception{

        String systemBookCode = "4020";
        String url = nhhosUtil.getUrl(systemBookCode);
        ReceiveOrderRpc receiveOrderRpc = nhhosUtil.createCenterObject(ReceiveOrderRpc.class, url);
        String orderState = "收货单";
        String orderFid = "PI4020990000146";
        ReceiveReturnOrderDTO result = receiveOrderRpc.findReceiveReturnOrderDetails(systemBookCode, orderFid, orderState);
        return result;

    }
    @RequestMapping(method = RequestMethod.GET,value = "/test7")
    public String test7() throws Exception{

        String url = nhhosUtil.getUrl(systemBookCode);
        PurchaseOrderRpc purchaseOrderRpc = nhhosUtil.createCenterObject(PurchaseOrderRpc.class, url);
        String systemBookCode = "4344";
        List<String> fids = new ArrayList<>();
        fids.add("PO4344990000698");
        purchaseOrderRpc.batchHandle(systemBookCode,fids);
        return "success";

    }


    @RequestMapping(method = RequestMethod.GET,value = "/test8")
    public SettlementPageDTO test8() throws Exception{
        String systemBookCode = "4344";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = sdf.parse("2018-04-01");
        Date dateTo = sdf.parse("2018-04-27");
        String url = nhhosUtil.getUrl(systemBookCode);
        SettlementRpc settlementRpc = nhhosUtil.createCenterObject(SettlementRpc.class, url);
        PreSettlementQueryData query = new PreSettlementQueryData();
        query.setSystemBookCode(systemBookCode);
        query.setStateCode(1);
        query.setDateFrom(dateFrom);
        query.setDateTo(dateTo);

        SettlementPageDTO orderBySupplier = settlementRpc.findOrderBySupplier(systemBookCode,query,0,50);
        return orderBySupplier;

    }
}
