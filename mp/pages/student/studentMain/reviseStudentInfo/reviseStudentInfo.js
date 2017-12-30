import api from '../../../../utils/reviseStudentInfoApi';
// pages/student/reviseStudentInfo/reviseStudentInfo.js
Page({
    onLoad: function (options) {
        const that = this;
        api.aboutMe(function (res) {
            that.setData({
                user: res
            });
        });
    },

    unbind() {
        wx.redirectTo({
            url: '/pages/bind/bind'
        });
    },

    changeAvator(){
        // todo add functionality
    }
});
