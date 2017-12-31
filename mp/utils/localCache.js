const example = {
    userID: 123,
    // courses: {1: (), 2: ()},
    user: {},
    // seminars: {1: (), 2: ()},
    currentCourseID: 1,
    currentSeminarID: 1,
    groupID: 1,
    group: {},

    currentSeminar: {
        "id": 29,
        "name": "界面原型设计",
        "courseName": "OOAD",
        "groupingMethod": "fixed",
        "startTime": "2017-09-25",
        "endTime": "2017-10-09",
        "classes": [
            {
                "id": 53,
                "name": "周三12"
            },
            {
                "id": 57,
                "name": "周三34"
            }
        ]
    }, // for teacher
    currentClass: {
        "id": 23,
        "name": "周三1-2节",
        "numStudent": 120,
        "time": "周三一二节",
        "site": "海韵201",
        "calling": -1,
        "roster": "/roster/周三12班.xlsx",
        "proportions": {
            "report": 50,
            "presentation": 50,
            "c": 20,
            "b": 60,
            "a": 20
        }
    }
};


const data = {};

const op = {
    set: function (key, value) {
        data[key] = value;
    },
    get: function (key) {
        return data[key];
    }
};

export default op;
