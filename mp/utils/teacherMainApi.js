// todo set cache(student)
import cache from './localCache';
import utils from './utils';

function getInfo(cb) {

    utils.requestWithId({
        url: '/me',
        success: function (response) {
            console.log(response);
            cache.set('me', response.data);
            cache.set('userID',response.data.id);
            utils.requestWithId({
                url: '/course',
                success: function (res) {
                    function cacheCourses() {
                        const courses = {};
                        //todo restore
                        res.data.map(course => {
                            courses[course.id] = course;
                    });

                        cache.set("courses", courses);
                    }

                    cacheCourses();

                    cb({'me': response.data, 'classes': res.data});
                }
            });
        }
    });
}

export default {getInfo};