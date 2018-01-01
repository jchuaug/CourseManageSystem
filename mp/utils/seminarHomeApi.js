import cache from './localCache';

function getSeminarInfo(seminarID, cb) {
    const courses = cache.get('courses');
    const courseName = courses[cache.get('currentCourseID')].name;
    const seminar = cache.get('seminars')[seminarID];
    seminar.courseName = courseName;

    cache.set('currentSeminarID', seminarID);

    cb(seminar);
}

export default {getSeminarInfo}