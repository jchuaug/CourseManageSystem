// pages/student/courseHome/courseHome.js
import api from '../../../utils/seminarHomeApi';
import utils from '../../../utils/utils';

import op from "../../../utils/localCache";


Page({
    data: {},


    onLoad: function (options) {
        /**
         * options{
         *   seminarId: 1
         * }
         */
        op.set('currentSeminarId',options.seminarId)

        const that = this;
        api.getSeminarInfo(options.seminarId, function (seminar) {
            const started = isSeminarStarted(seminar);
            that.setData({
                seminar: seminar,
                started: started,
                courseName: seminar.courseName,
                seminarId: options.seminarId
            });
        });
    },

    callInRoll(e) {
        const targetUrl = utils.buildUrl({
            base: './rollCall/rollCall',
            seminarId: this.data.seminarId
        });

        wx.navigateTo({
            url: targetUrl
        });
    },

    grouping() {
        const targetUrl = utils.buildUrl({
            base: './group/group'
        });

        wx.navigateTo({
            url: targetUrl
        });
    },

    scoring() {
        wx.navigateTo({
            url: './grade/grade'
        });
    }

});

function isSeminarStarted(res) {
    const startTime = Date.parse(res.startTime);
    const endTime = Date.parse(res.endTime);
    const now = Date.now();
    let started = false;
    if (now >= startTime && now <= endTime) {
        started = true;
    }
    return started;
}
