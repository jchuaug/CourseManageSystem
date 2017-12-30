import api from '../../../../../utils/chooseTopicApi';

Page({
    data: {},

    onLoad() {
        const that = this;
        api.getTopics(function (topics) {
            console.log(topics);
            that.setData({
                topics: topics,
                opened: {}
            });
        });
    },

    expand(e) {
        const opened = this.data.opened;
        opened[e.target.id] = !opened[e.target.id];
        this.setData({"opened": opened});
    },

    chooseTopic(e) {
        const id = e.currentTarget.id;
        wx.showModal({
            title: '提示',
            content: '是否选择当前话题',
            success: function (res) {
                if (res.confirm) {
                    api.chooseTopic(id, function (res) {
                        if (res === true) {
                            wx.navigateBack();
                        }
                    });
                }
            }
        })
    }
});