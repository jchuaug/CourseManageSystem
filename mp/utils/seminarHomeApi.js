import cache from './localCache';
import utils from './utils';

function getSeminarInfo(seminarID, cb) {
    const courses = cache.get('courses');
    const courseName = courses[cache.get('currentCourseID')].name;
    const seminar = cache.get('seminars')[seminarID];
    seminar.courseName = courseName;
    cache.set('currentSeminarID', seminarID);


    // get and cache group info
    // first get group id

    utils.requestWithId({
        url: `/seminar/${seminarID}/group/my`,
        success: function (res) {
            const groupID = res.data.id;
            cache.set('groupID', groupID);
            cache.set('group', res.data);
            cb(seminar);
        }
    });
}

export default {getSeminarInfo}