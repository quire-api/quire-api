var stCurrentUser = "curusr",
	isUser = false;

function _showHeaderIcon() {
	$('#header-icons').removeClass('hide');
}

function _updateLoginUser(response) {
	var readS3 = getLocalStorage("inCgf") != null ? aws2quire: identity,
	    user = $('.t-user'),
		userId = response['id'],
		userName = response['name'],
		initials = response['initials'],
		userImg = readS3(response['image']),
		iconColor = response['iconColor'];

	isUser = true;

	// update for icon and its dropdown
	user.find('.dropdown-header').text(userName);
	user.find('.user-icon-wrapper').append(
		userImg != '' ?
			$('<img class="img-icon x30 circle" src="' + userImg + '" alt="' + userName + '">') :
			$('<div class="img-icon x30 circle bg iconc-' + iconColor +'">' + initials + '</div>')
	);
	user.find('.icon-user-o').parent().attr('href', '/u/' + userId);

    $('#header-wrapper').addClass('qr-collapse-navbar-768');
	// remove stuff for non-user
	$('li.login').remove();

	$('.workspace').removeClass('hide');

	user.removeClass('hide');
	_showHeaderIcon();
}

function _updateNonLoginUser() {
	// remove stuff for user
	$('#header-wrapper').addClass('qr-collapse-navbar');
    $('li.workspace, li.t-user').remove();

    $('.login').removeClass('hide');

	_showHeaderIcon();
}

window.sendQS = function (data, callback, failCallback) {
	var channel = $('body').data('qs') || 'qs';
		//#7994: for chrome extension, we have to use /qs
//	var segs = location.pathname.match(/^\/([^/]+)\//);

	return $.ajax({
		type: 'POST',
		url: '/' + channel,
			//for chrome extension, we need to specify absolute path
		data: JSON.stringify(data),
		contentType: "application/json;charset=UTF-8",
//		headers: {"x-spg": segs ? segs[1]: ''}
	})
	.done(callback)
	.fail(function (xhr) {
		if (failCallback)
			failCallback(xhr);
		else {
			var msg = netErrorMsg(xhr);
			if (msg)
				showAlert($('#body'), msg, 'alert-danger');
		}

	});
};

///URL of our files in Amazon S3
var s3AwsUrl = "https://quire.s3.amazonaws.com";
/// The prefix that was mapped to [s3AwsUrl]
var quireS3Prefix = "/quis3";

/// Whether the given path is [s3AwsUrl].
function isAwsS3Path(path) {
    return path && (path.startsWith(s3AwsUrl+'/') || path == s3AwsUrl);
}

function aws2quire(path) {
    return isAwsS3Path(path) ? quireS3Prefix+path.substring(s3AwsUrl.length): path;
}

function identity(v) {
    return v;
}


$(function() {
  // $(document).foundation();

  var $sidebar = $('#sidebar');
  if ($sidebar.length) {
    var $docs = $('#docs');
    var $nav = $sidebar.find('nav');

    //
    // Setup sidebar navigation
    var traverse = new Traverse($nav, {
      threshold: 10,
      barOffset: $sidebar.position().top
    });

    $nav.on('update.traverse', function(event, element) {
      $nav.find('section').removeClass('expand');
      var $section = element.parents('section:first');
      if ($section.length) {
        $section.addClass('expand');
      }
    });

    //
    // Bind the drawer layout
    var $drawerLayout = $('.drawer-layout'),
      $drawer = $drawerLayout.find('.drawer'),
      closeDrawer = function() {
        $drawer.addClass('sliding');
        $drawer.removeClass('slide-right slide-left');
        $drawer.find('.drawer-overlay').remove();
        $drawerLayout.removeClass('drawer-open drawer-slide-left-large');

        return false;
      };

    $drawer.on('transitionend webkitTransitionEnd oTransitionEnd', function () {
        $drawer.removeClass('sliding');
    });

    // Drawer open buttons
    $drawerLayout.find('[data-drawer-slide]').click(function(e) {
      var $this = $(this),
        direction = $this.data('drawer-slide');
      $drawerLayout.addClass('drawer-slide-right-large');
      $drawer.addClass('slide-' + direction);
      $drawer.addClass('sliding');

      var $overlay = $('<a href="#" class="drawer-overlay"></a>')
      $drawer.append($overlay);
      $overlay.click(closeDrawer);

      return false;
    });

    // Drawer close buttons
    $drawerLayout.find('[data-drawer-close]').click(closeDrawer);
  }

  sendQS([stCurrentUser, null], function(response) {
    if (response)
      _updateLoginUser(response);
    else
      _updateNonLoginUser();
  }, function() {
    _updateNonLoginUser();
    _showHeaderIcon();
  });
});
