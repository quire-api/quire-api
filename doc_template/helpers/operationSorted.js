var Handlebars = require('handlebars')

module.exports = function(context, options) {
  var ret = "";
  var methodOrder = ['post','get','put','delete'];
  var pathOrder = ['id','{oid}','list', 'search', 'after', 'before'];
  var data;
  if (typeof context !== "object") {
    return ret;
  }
  var keys = Object.keys(context)
  keys.sort(function(a,b) {
    var aMethod = methodOrder.indexOf(context[a].method)
    var bMethod = methodOrder.indexOf(context[b].method)
    if (aMethod == bMethod) {
        var aPath = pathOrder.indexOf(context[a].path.split("/")[2])
        var bPath = pathOrder.indexOf(context[b].path.split("/")[2])

        return aPath - bPath;
    }

    return aMethod - bMethod;
  }).forEach(function(key, index) {
    //console.log(context[key].method + ', ' + context[key].path);
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
