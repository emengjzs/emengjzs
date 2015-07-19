
var institute_source =
    {
        datatype: "json",
        datafields:[
            {name: 'i', type: 'string'},
        ],
        url: "institute",
    };

var institutedataAdapter = new $.jqx.dataAdapter(institute_source);

var get_institute_dropdownlist_config =     
{
        theme: "bootstrap", /*height: 35, */autoDropDownHeight: true,
        selectedIndex: -1, source: institutedataAdapter, displayMember: "i", valueMember: "i"
};

var init_institute_dropdown = function () {


    // Create a jqxDropDownList
    $(".institute-dropdown").jqxDropDownList(get_institute_dropdownlist_config);
};
