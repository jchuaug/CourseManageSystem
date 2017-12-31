import api from '../../../../utils/groupApi';
import utils from '../../../../utils/utils';

Page({
    data: {},

    onLoad() {
        console.log("loading");
        this.load();
    },

    load(seminarId) {
        const that = this;
        api.getGroupInfo(function (res) {
            that.setData({
                leader: res.leader ? res.leader : null,
                members: res.members,
                id: res.id,
                topics: res.topics,
                isLeader: api.amILeader(),
                seminarId: seminarId,
            });
        });
    },

    onShow() {
        const that = this;
        if (this.data.hide) {
            console.log("reloading");
            api.getGroupInfo(function (res) {
                that.setData({
                    topics: res.topics
                })
            })
        }
    },

    ohHide() {
        this.setData({'hide': true})
    },


    becomeLeader() {
        const that = this;

        api.becomeLeader(function (res) {
            if (res) {
                that.setData({
                    leader: res.leader ? res.leader : null,
                    members: res.members,
                    isLeader: api.amILeader(),
                });
            }
        });
    },

    quitLeader() {
        const that = this;

        api.quitLeader(function (res) {
            if (res) {
                that.setData({
                    leader: res.leader ? res.leader : null,
                    members: res.members,
                    isLeader: api.amILeader(),
                });
            }
        });
    },

    chooseTopic() {

        const targetUrl = utils.buildUrl({
            base: './chooseTopic/chooseTopic',
            seminarId: this.data.seminarId
        });

        this.setData({
            'hide': true
        });

        wx.navigateTo({
            url: targetUrl
        });
    }
});