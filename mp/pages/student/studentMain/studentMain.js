import api from "../../../utils/studentMainApi";
import cache from '../../../utils/localCache';
import utils from "../../../utils/utils";
// pages/student/studentMain/studentMain.js
Page({
    onLoad() {
        const that = this;
        api.getInfo(function (res) {
            that.setData({
                me: res.me,
                classes: res.classes
            });
        });
    },

    goRevise: function () {
        wx.navigateTo({
            url: './reviseStudentInfo/reviseStudentInfo'
        });
    },

    /**
     * 选中某一个课程进入其课程主页面
     */
    chooseCourse: function (e) {
        console.log(e);

        const dataset = e.currentTarget.dataset;
        console.log("dataset", dataset);

        const targetUrl = utils.buildUrl({
            base: '../oneCourse/oneCourse',
            classId: dataset.courseId
        });
        cache.set('classId', dataset.courseId);
        wx.navigateTo({
            url: targetUrl
        });
    }
});
