// pages/teacher/classManage/classManage.js
import api from '../../../utils/classManageApi.js'

import op from "../../../utils/localCache";


//这个页面需要上级页面传入一个courseid
//这里先在data里虚拟一个course_id

Page({


    /**
     * 页面的初始数据
     */
    data: {},

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        // courseId
        const courseID = options.courseId || 1;
        console.log(options);

        const that = this;

        api.getInfo(courseID, function (res) {
            res.startTime = that.getShortDate(res.startTime);
            res.endTime = that.getShortDate(res.endTime);
            that.setData({
                seminar: res,
                courseID: courseID
            });
        });

        // api.getCourseInfoByCourseId(this.data.course_id, function (value) {
        //     that.setData({
        //         courseinfo: value
        //     })
        // });
        // api.getSeminarArrByCourseId(this.data.course_id, function (value) {
        //     这里value获取的是某个课程的讨论课列表数组，而页面只需要当前正在进行的讨论课
        //     ！！这里还需完善，如何获取当前讨论课
        //
        // value[0]是固定分组，value[1]是随机分组，在这里改变值以演示页面
        // let curSeminar = value[0];
        // 返回的日期太长了，去掉年份

        // api.getClassArrByCourseId(this.data.course_id, function (value) {
        //     that.setData({
        //         classes: value
        //     })
        // })

    },

    getShortDate(date) {
        let cutPoint = date.indexOf('-') + 1 || 5;
        return date.slice(cutPoint);
    },

    toStartCall: function (e) {
        let toclassid = e.currentTarget.dataset.classid;
        let groupMethod = e.currentTarget.dataset.groupmethod;
        console.log('点击了班级id:' + toclassid + '分组方式:' + groupMethod);
        wx.navigateTo({
            url: '../rollStartCall/rollStartCall?classID=' + toclassid + '&groupMethod=' + groupMethod,
        });
    }
});

