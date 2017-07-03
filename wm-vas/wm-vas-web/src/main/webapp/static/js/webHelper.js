WebHelper = {
    GetLogisticAddress: function () {
        return "http://share.tsh365.cn/share/getAddressByName.do";
    },
   
    GetAddress: function () {
        return "http://share.tsh365.cn/share/getAddress.do";
    },

   
    getAddressByLevel: function (level) {
        var origin = location.origin;
        if (origin.indexOf("tsh365.cn") > 0) {
            return "http://share.tsh365.cn/share/getAddressLevel.do?level=" + level;
        } else {
            return "http://172.16.1.97:8992/share/getAddressLevel.do?level=" + level;
        }
    },

    
    UploadImage: '../../common/uploadimg.do',
   
    GetImgCode: '../../verify/code.do',
   
    GetVerifyCode: '../../supplier/getVerifyCode.do',
  
    Regist: '../../supplier/regist.do',
    
    GetSupplier: '../../supplier/getSupplier.do',
   
    GetAreasByProviceAndCity: '../../area/getAreasByProvinceAndCity.do',
    
    SubmitSupplierData: '../../supplier/submitSupplierData.do',
   
    ApplyCrossSale: '../../supplier/applyCrossSale.do',
   
    AddArea: '../../area/addArea.do',
  
    GetAreaInfoById: '../../area/getAreaInfoById.do',
    
    UpdateAreaInfo: '../../area/updateAreaInfo.do',
   
    AreaList: '../../area/getAreaList.do',
    
    ExportAreaList: '../../area/exportAreaInfoList.do',
    
    GetSupplierById: '../../supplier/getSupplierById.do',
   
    QueryLog: '../../log/queryLog.do'
};