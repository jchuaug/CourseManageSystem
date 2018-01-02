// pages/student/courseHome/courseHome.js
import api from '../../../../utils/rollCallApi';
import utils from '../../../../utils/utils';


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
        const that = this;
        api.callInRoll(function (res) {
            that.setData({
                state: res.state
            });


            utils.showSuccessToast();

            setTimeout(function () {
                wx.navigateBack();
            }, 2000);
        });
    }
});
