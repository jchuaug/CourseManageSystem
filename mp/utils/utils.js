import constants from "./constants";
import cache from "./localCache";

function buildUrl(args) {
    let baseUrl = args.base;

    let firstParam = true;
    for (const key in args) {
        if (key !== 'base') {
            baseUrl += `${firstParam ? '?' : '&'}${key}=${args[key]}`;
            firstParam = false;
        }
    }
    return baseUrl;
}

function showSuccessToast(cb) {
    wx.showToast({
        title: '成功, 请稍等',
        icon: 'success',
        duration: 2000,
        success: cb
    });
}

function failToast(msg) {
    wx.showToast({
        title: msg,
        // icon: 'fail',
        image: '/images/error.png',
        duration: 2000
    });
}

function requestWithId(args) {
    wx.request({
        url: constants.domain + args.url,
        header: {'id': cache.get('userID')},
        method: args.method ? args.method : 'get',
        data: args.data,
        success: args.success,
        fail:function () {
            utils.failToast('fail');
        }
    });
}

export default {buildUrl, showSuccessToast, failToast, requestWithId}