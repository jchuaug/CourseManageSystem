import api from '../../../utils/reviseTeacherInfoApi';
// pages/teacher/reviseTeacherInfo/reviseTeacherInfo.js
Page({

    /**
     * 页面的初始数据
     */
    data: {},

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        const that = this;
        api.aboutMe(function (res) {
            that.setData({
                user: res
            });
        });
    },

    unbind() {
        api.unbind(function (res) {
            if (res) {
                wx.reLaunch({
                    url:'/pages/bind/bind'
                });
            }
        })
    }
});