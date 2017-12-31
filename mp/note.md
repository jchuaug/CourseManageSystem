* 每个页面在单独的文件夹，如果是老师相关的放在teacher 下面，学生相关的放在学生下面，最外面是没有特别分老师和学生，或者说差别不大的页面

* 页面里面的数据不要写死在页面中，比如说，课程列表，不要把所有的课程写在课程列表中，要在课程列表的 js 中调用 getAllCourse() 函数，之后再把这些数据显示出来，这个函数放在 util 里面的 **页面+api.js** 文件里面，自己创建
  api 的形式为 api(args,callback), args 表示参数，callback 为回调函数，如果没有参数或者回调，则填入null

  ```js
  import api from '/utils/studentMainApi.js';
  Page({
    ...
    ...
    const that = this; // that 存储当前的this， 供回调函数使用，这是必要的
    onLoad(){
      api.getUserInfo(null, function(result){
            that.setData({
                user: result
            });
        }
      });
    }
  })
  ```

* 页面之间传递参数不超过两个，最好一个（id，比如seminarId，courseId），其他的由api文件获取，可以参考，**在onload 函数开头注释说明传进来的参数**，方便日后debug

* 在 utils／utils.js 有一个 buildUrl 的函数，可以方便的组合url

  使用方法：

  ```js
    const targetUrl = utils.buildUrl({
              base: './chooseTopic/chooseTopic',
              seminarId: this.data.seminarId
          });
  ```

  base 是基本的url，之后要加的参数就直接添加

  ​

* api 返回的数据按照标准组的来



  使用方法：

  ```js
    const targetUrl = utils.buildUrl({
              base: './chooseTopic/chooseTopic',
              seminarId: this.data.seminarId
          });
  ```

  base 是基本的url，之后要加的参数就直接添加

  ​

* api 返回的数据按照标准组的来
