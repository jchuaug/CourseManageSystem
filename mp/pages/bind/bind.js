import api from "../../utils/bindApi";
import utils from "../../utils/utils";

Page({
    data: {
        userType: 'student',
        schoolArray: [
            ['福建省'],
            ['厦门式'],
            ['集美大学', '厦门大学']
        ],
        schoolIndex: [0, 0, 1],
        school: '厦门大学'
    },

    onLoad: function (options) {
        this.setData({});
    },

    changeUserType(e) {
        let userType = '';
        if (e.target.id === 'bt_student') {
            userType = 'student';
        } else {
            userType = 'teacher';
        }

        this.setData({
            "userType": userType
        });
    },

    textInput(e) {
        // console.log(this.data);
        this.setData({
            [e.target.id]: e.detail.value
        });
    },

    schoolPickerChange(e) {
        this.setData({
            schoolIndex: e.detail.value
        });


        this.setData('school', this.data.schoolArray[2][e.detail.value[2]]);
    },

    bt_bind() {
        const that = this;
        console.log(this.data.school);

        api.bindUser({
            'name': this.data.name,
            'number': this.data.number,
            'school': {'name': this.data.school, 'id': 12}
        }, function (res) {
            if (res) {
                let targetUrl = '';
                if (that.data.userType === 'student') {
                    targetUrl = '/pages/student/studentMain/studentMain';
                } else {
                    targetUrl = '/pages/teacher/teacherMain/teacherMain';
                }

                utils.showSuccessToast(
                    function () {
                        wx.redirectTo({
                            url: targetUrl
                        })
                    }
                );
            } else {
                utils.failToast('info error');
            }
        });
    }
});
