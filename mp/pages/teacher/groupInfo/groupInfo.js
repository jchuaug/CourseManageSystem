// pages/teacher/groupInfo/groupInfo.js
import api from '../../../utils/groupInfoApi.js'

Page({

    /**
     * 页面的初始数据
     */
    data: {
        groupMethod: '',
        groups: [],
        latesArr: null
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        // let classId = options.classID
        // let seminarId = options.cursemid
        let groupMethod = options.groupmethod;
        const that = this;


        function getGroupDetailPromisify(groupId) {
            return new Promise(resolve => {
                api.getGroupDetail(groupId, res => {
                    if (res.leader) {
                        res.members.unshift(res.leader);
                    }
                    resolve(res); 
                });
            });
        }

        //不是固定分组的话才需要显示迟到学生
        if (groupMethod != 'fixed') {
            api.getLateStudent((value) => {
                this.setData({
                    latesArr: value.late
                });
            });
        }

        api.getGroups((value) => {
            const promiseArray = value.map(group => getGroupDetailPromisify(group.id));
            Promise.all(promiseArray).then(groups => {
                //todo  接着这里
                console.log("组信息");
                console.log(groups);

                that.setData({
                    groupMethod: groupMethod,
                    groups: groups
                });

                console.log(this.data.groups);
            });
        });


    },

    toggle: function (e) {
        let tapId = e.currentTarget.dataset.groupid
        let list = this.data.groups

        for (let i = 0, len = list.length; i < len; ++i) {
            if (list[i].id == tapId) {
                list[i].shown = !list[i].shown
            }
        }
        this.setData({
            groups: list
        });
    },

    addMember: function (e) {
        console.log(e)

        var that = this;

        let tapId = e.currentTarget.dataset.groupid
        let list = this.data.groups

        let templist = this.data.latesArr
        let latelist = []
        for (let i = 0; i < templist.length; i++) {
            latelist.push(templist[i].name)
        }

        wx.showActionSheet({
            itemList: latelist,
            success: function (res) {
                if (res.tapIndex !== undefined) {
                    console.log(res.tapIndex)
                    let people = templist[res.tapIndex]
                    console.log(people)
                    for (let i = 0, len = list.length; i < len; ++i) {
                        if (list[i].id == tapId) {
                            console.log(list[i])
                            list[i].members.push(people)
                        }
                    }
                    templist.splice(res.tapIndex, 1)

                    that.setData({
                        groups: list,
                        latesArr: templist
                    })
                }
            },
            fail: function (res) {
                console.log(res.errMsg)
            }
        })
    }

})