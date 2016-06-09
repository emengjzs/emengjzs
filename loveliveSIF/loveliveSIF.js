(function(){
// start 

$.ajax({url : "cardData.js",
        type : "GET", 
        dataType: "text",
        success: function(data) {
			myVueApp(data);
		}
});

function mainCardDataFilter(card) {
	return $.inArray(card.rarity, ['UR']) > -1;
};

function rarityUpFilter(rarity) {
	var rlist = ['N', 'R', 'SR', 'UR']
	rlist = rlist.slice(rlist.indexOf(rarity), 4);
	return function(card) {
		return $.inArray(card.rarity, rlist) > -1;
	};
};

var fp = (function() {


	var acc_func = function(f, l) {
		var len = f.length;
		var p1 = l[0];
		for (var i = 1; i < l.length; i = i + len - 1) {
			p1 = f.apply(null, [p1].concat(l.slice(i, i + len - 1)));
		}
		return p1;
	};


	var chain_func = function (f, o, l) {
		l.forEach(function(item){
			o = f.apply(o, item);
		});
		return o;
	};

	return {
		acc_func: acc_func,
		chain_func: chain_func
	};
})();




function getMainCardData(data) {
	var JSONCardData = (function(l) {
		$.each(JSON.parse(fp.chain_func(data.replace, data, [ 
			[new RegExp("&#34;", "g"), '"'],
			[new RegExp("&#39;", "g"), "'"],
			[new RegExp("&amp;", "g"), '&']])),
			function(key, value) {
				l.push(value);				
			});
		return l;
	})([]);
	return JSONCardData;
}

function myVueApp(data) {
	var data = getMainCardData(data).filter(rarityUpFilter('UR'));
	var mycards = [];
	console.log(data);
	new Vue({
		el: '#app',
		data: {
			'album': data,
			'mycards': mycards,
		},
	});
};



//end
})();
