import api from "../../../utils/teacherMainApi";
import utils from "../../../utils/utils";
// pages/teacher/teacherMain/teacherMain.js
Page({
    /**
     * 页面的初始数据
     */
    data: {},

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function () {
        const that = this;
        api.getInfo(function (res) {
            that.setData({
                me: res.me,
                classes: res.classes
            });
        });
    },


    /**
     * 跳转到老师的解绑页面
     */
    goRevise: function () {
        wx.navigateTo({
            url: '../reviseTeacherInfo/reviseTeacherInfo'
        })
    },


    /**
     * 选中某一个课程进入其课程主页面
     */
    chooseCourse: function (e) {
        const dataset = e.currentTarget.dataset;
        console.log(dataset);
        const targetUrl = utils.buildUrl({
            base: '../classManage/classManage',
            courseId: dataset.courseId
        });

        wx.navigateTo({
            url: targetUrl
        });
    }
});