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

        locData: {
            longtitude: null,
            latitude: null
        }
    },

    onLoad: function (options) {
        const classID = options.classID;
        const groupMethod = options.groupMethod;

        const that = this;

        


        function refreshData(){
          
          api.getCallingStatus(function(res){
            console.log(res)
            that.setData({
              'currentClass.present':res.numPresent,
              'currentClass.numStudent':res.numStudent,
            })
          })

          api.getLocationStatus(function(res){
            console.log('后台签到状态',res.data.status)
            if (res.data.status == 2) {
              that.setData({
                call: {
                  status: "end",
                  btnStatusText: "签到名单"
                }
              })
            }
          })

        }

        setInterval(refreshData,3000)

        api.getClassByClassId(classID, function (value) {
            console.log("class ", value);
            that.setData({
                classID: classID,
                currentClass: value
            });


            // get class roll call status
            api.getClassStatus(function (status) {
                switch (status) {
                    case "calling":
                        that.setData({
                            call: {
                                status: "calling",
                                btnStatusText: "结束签到"
                            }
                        });
                        break;
                    case "end":
                        that.setData({
                            call: {
                                status: "end",
                                btnStatusText: "签到名单"
                            }
                        });
                        break;
                }
            });
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
            success: function (res) {
                const attendance = {};
                attendance.longtitude = res.longitude;
                attendance.latitude = res.latitude;
                attendance.status = 0;
                api.putLocation(attendance, function () {
                    console.log("位置信息put成功");
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
                })
            },
        });
        //
        // api.putCurClassCalling({classID: this.data.classID, "calling": this.data.currentSeminar.id}, function (res) {
        //
        // });
    },

//callback hell想办法用promise或者async来解决这个问题
    endCall: function () {
        const that = this;
        let attendance = {status:2}
        api.putLocation(attendance,function(){
          console.log("结束点名的信息已经传送");
        })
        wx.showModal({
            title: '提示',
            content: '确定要结束点名',
            success: (res) => {
                if (res.confirm) {
                    api.putCurClassCalling({"classID": this.data.classID, "calling": -1}, () => {
                        that.setData({
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
