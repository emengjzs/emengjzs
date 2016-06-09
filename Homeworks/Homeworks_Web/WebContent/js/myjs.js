var changepage = function(cin, cout, url) {
    cin = cin ? cin : '#contents-in';
    cout = cout ? cout : "#contents-out";
    $.ajax({
            type: 'GET',
            url:  url,
            dataType: 'html',
            
            success: function(data) { 
                $(cin).remove();
                $(data).appendTo(cout);    
            },
        });
};


var addClickListener = function(str, cin, cout) {
        $(str).each(function(i) {
            if($(this).attr('for') != "") {
                $(this).click(function() { 
                    changepage(cin,cout, $(this).attr('for'));
                });
            }
        });
};



var get_selected_id = function (jq) {
    var idfield = $(jq).attr("idfield");
    var indexes = $(jq).jqxGrid('getselectedrowindexes');
    var ids= new Array();
    for(var i=0; i < indexes.length; i ++) {
        ids.push($(jq).jqxGrid('getrowdata', indexes[i])[idfield]);
    }
    console.log(ids);
    return ids;
}

var init_add_table = function(para) {
    //var idfiled = para['id'];
    var selectbox = para['selectbox'];
    var selectionmode = selectbox ? 'checkbox': 'none';
    var columns = para['columns'];
    var add_table_columns =  [];
    var datafields =[];

    for(c in columns) {
        col = columns[c];
        col['cellsAlign'] = 'center',
        col['align'] = 'center',
        add_table_columns.push(col);

        var colfield ={'name': col['dataField'], 'type': col['type']};
        if(col['format']) {
            colfield['format'] = col['format'];
        } 
        datafields.push(colfield);
    }


    var add_table_source = 
    {   
        url: para['url'],
        datatype: "json",
        datafields: datafields,
        id: para['id'],
    };  

    //console.log(add_table_columns);    
    var adddataAdapter = new $.jqx.dataAdapter(add_table_source);
    $(para['jq']).attr("idfield", para['id']);
    $(para['jq']).jqxGrid(
    {   theme: "bootstrap",
        source: adddataAdapter,
        altRows: true,
        sortable: true,
        scrollmode: 'default',
        enablehover: false,
        selectionmode: selectionmode,
        width:"100%",
        height: 400,
        columnsheight: 38,
        rowsheight: 35,
        columns:add_table_columns,
        showfilterrow: true,
        filterable: true,
    });  

} ;


var idMaps= {
    'student': 'sid',
    'teacher': 'tid',
    'tuitor' : 'sid',
    'lesson' : 'lid',
    'assignment': 'aid'
};


var get_student_columns = function() {
    return [
        { text: '学号', dataField: 'sid', type: 'number', width: '30%'},
        { text: '姓名', dataField: 'name', type: 'string'},
        { text: '院系', dataField: 'institute',type: 'string', filtertype: 'checkedlist'},
        { text: '入学年份', cellsformat: 'yyyy',dataField: 'year', type: 'date' , format: "yyyy"},
    ];
};

var get_tuitor_columns = get_student_columns;

var get_teacher_columns = function() {
    return [
        { text: '工号', dataField: 'tid', type: 'number', width: '30%'},
        { text: '姓名', dataField: 'name', type: 'string'},
        { text: '院系', dataField: 'institute',type: 'string', filtertype: 'checkedlist'},
    ];
};

var get_institute_config = function() {
    var institute_source =
    {
      datatype: "json",
      datafields:[
            {name: 'i', type: 'string'},
      ],
      url: "institute",
      async: false,
    };
    var institutedataAdapter = new $.jqx.dataAdapter(institute_source);
    var config = {
        animationType: 'fade',
        theme: "bootstrap", 
        /*height: 35, */
        autoDropDownHeight: true,
        //selectedIndex: -1,
        source: institutedataAdapter,
        displayMember: "i", 
        valueMember: "i"
                            
    };

    return config;
}; 


var initnotify = function() {
    //$('#messagenotify').jqxNotification({
    //            theme: 'bootstrap',
    //            width: 250, position: "bottom-left", opacity: 0.9,
//autoOpen: false, animationOpenDelay: 800,  autoClose: false, autoCloseDelay: 1500,
   // });
};

var shownotify = function(message) {
    
    if(message.success) {
        $('#messagenotify').toastmessage('showToast',{
            text : message.info,
            position: 'top-right',
            type : 'success',
        });

    }
    else {
         $('#messagenotify').toastmessage('showToast',{
            text : message.info,
            position: 'top-right',
            type : 'warning',
        });
    }
     
};


var getcolarray = function(array, index) {
    var r = [];
    for(i in array) {
        r.push(array[i][index]);
    }
    console.log(r);
    return r;
};

var refreshprogress = function(notify) {
  $.ajax({
            type: 'GET',
            url:  'notify',
            dataType: 'json',
            
            success: function(data) { 
                if(notify) {
                    shownotify(data);
                }
                $('#notify').text(data.info);
            },
    });
}
