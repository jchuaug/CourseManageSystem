// pages/student/courseHome/courseHome.js
import api from '../../../../utils/rollCallApi';


Page({
    data: {},


    onLoad: function (options) {
        /**
         *  seminarId
         */

        console.log(options);
        const that = this;
        api.getSeminarInfoById(options, function (seminar) {
            that.setData({
                seminar: seminar
            });
        });
    },

    iAmHere() {
        const that = this
        api.callInRoll(function (res) {
           console.log("签到后返回",res)
            that.setData({
                state: res.state
            });
        });
    }
});
