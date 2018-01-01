// pages/teacher/rollStartCall.js
import api from '../../../utils/rollStartCallApi.js'
import cache from "../../../utils/localCache";



Page({

    data: {
        classID: 0,
        currentClass: {},
        call: {
            status: "start",
            btnStatusText: "开始签到"
        },
        locData:{
          longtitude:null,
          latitude:null
        }
    },

    onLoad: function (options) {
        const classID = options.classID;
        const groupMethod = options.groupMethod;

        const that = this;

        api.getClassByClassId(classID, function (value) {
            console.log(value);
            that.setData({
                classID: classID,
                currentClass: value
            })
        });


        const currentSeminar = api.getCurrentSeminar();
        this.setData({
            currentSeminar: currentSeminar,
            groupingMethod: groupMethod
        });
    },

    changeStatus: function (e) {
        switch (this.data.call.status) {
            case "start":
                this.startCall();
                break;
            case "calling":
                this.endCall();
                break;
            case "end":
                this.checkCall(e);
                break;
        }
    },

    startCall: function () {
        const that = this;
        wx.getLocation({
          success: function(res) {
            var loc= {}
            loc.longtitude = res.longitude
            loc.latitude = res.latitude
            api.putLocation(loc,function(){
              console.log("位置信息put成功")
            })            
          },
        })
        api.putCurClassCalling({classID: this.data.classID, "calling": this.data.currentSeminar.id}, function (res) {
            that.setData({
                call: {
                    status: "calling",
                    btnStatusText: "结束签到"
                }
            });

            wx.showToast({
                title: '开始点名',
                duration: 800
            });
        });
    },

//callback hell想办法用promise或者async来解决这个问题
    endCall: function () {
        wx.showModal({
            title: '提示',
            content: '确定要结束点名',
            success: (res) => {
                if (res.confirm) {
                    api.putCurClassCalling({"classID": this.data.classID, "calling": -1}, () => {
                        this.setData({
                            call: {
                                status: "end",
                                btnStatusText: "签到名单"
                            }
                        });
                    });
                }
            }
        });
    },

    checkCall: function (e) {
        let classId = this.data.classID;
        let callStatus = e.currentTarget.dataset.callstatus;
        wx.navigateTo({
            // todo remove callstatus
            url: '../rollCallList/rollCallList?classID=' + classId + '&callstatus=' + callStatus,
        })
    },

    checkGroup: function (e) {
        let classId = e.currentTarget.dataset.classID;
        let groupMethod = e.currentTarget.dataset.groupmethod;
        wx.navigateTo({
            url: '../groupInfo/groupInfo?classID=' + classId + '&groupmethod=' + groupMethod,
        })
    }
});
