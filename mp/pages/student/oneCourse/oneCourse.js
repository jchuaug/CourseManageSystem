// pages/student/oneCourse/oneCourse.js
import api from '../../../utils/oneCourseApi';
import utils from '../../../utils/utils';


Page({
    data: {},

    onLoad: function (options) {
        console.log('=====');
        console.log(options);
        const that = this;

        api.getSeminarInfoByCourseId({id: options.classId}, function (res) {
            const seminars = res.seminars;
            seminars.map(seminar=>{
                seminar.active = that.isSeminarStarted(seminar);
            });

            console.log(seminars);

            that.setData({
                courseId: options.classId,
                courseName: res.courseName,
                seminars: seminars
            });
        });
    },

    chooseSeminar: function (e) {
        const dataset = e.target.dataset;
        const targetUrl = utils.buildUrl({
            base: '../seminarHome/seminarHome',
            courseId: this.data.courseId,
            seminarId: dataset.seminarId
        });

        wx.navigateTo({
            url: targetUrl
        });
    },

    viewCourseInfo() {
        wx.navigateTo({
            url: './courseInfo/courseInfo'
        });
    },

    isSeminarStarted(res) {
        const startTime = Date.parse(res.startTime);
        const endTime = Date.parse(res.endTime);
        const now = Date.now();
        let started = false;
        if (now >= startTime && now <= endTime) {
            started = true;
        }
        return started;
    }
});
