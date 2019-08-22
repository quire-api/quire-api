var Handlebars = require('handlebars')

module.exports = function(context, options) {
  var ret = "";
  var order = ['post','get','put','delete'];
  var data;
  if (typeof context !== "object") {
    return ret;
  }
  var keys = Object.keys(context)
  keys.sort(function(a,b) {
    a = order.indexOf(context[a].method)
    b = order.indexOf(context[b].method)
    if (a == b) return 0;
    if (a > b) return 1;
    return -1;
  }).forEach(function(key, index) {
    if (options.data) {
      data = Handlebars.createFrame(options.data || {})
      data.index = index;
      data.key = key;
      data.length = keys.length;
      data.first = index === 0;
      data.last = index === keys.length - 1;
    }
    ret = ret + options.fn(context[key], {data: data})
  })
  return ret;
};
