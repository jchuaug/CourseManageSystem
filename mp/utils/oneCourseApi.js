import utils from './utils';
import cache from './localCache';

function getSeminarInfoByCourseId(args, cb) {
    utils.requestWithId({
        url: `/class/${args.id}/seminar`,
        success: function (res) {
            cacheSeminars(res.data);
            cache.set('currentCourseID', args.id);

            cb({
                courseName: cache.get('courses')[args.id].name,
                seminars: res.data
            });
        }
    });
}

function cacheSeminars(seminars) {
    const cached = {};
    seminars.map(seminar => {
        cached[seminar.id] = seminar;
    });

    cache.set('seminars', cached);
}

export default {getSeminarInfoByCourseId}