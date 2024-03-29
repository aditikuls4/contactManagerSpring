console.log("this is script loading");

const toggleSideBar=()=>{
	
	if($(".sidebar").is(":visible"))
	{
		
		$(".sidebar").css("display","none");
		$(".content").css("margin-left","0%");
	}
	else
	{
		$(".sidebar").css("display","block");
		$(".content").css("margin-left","20%");
		
	}
};

const search=()=>{
	
	let query=$("#search-input").val();
	console.log(query);
	if(query=="")
	{
		$(".search-result").hide();
	}
	else
	{
	console.log(query)
	
	//sending the search req
	
	let url=`http://localhost:8080/search/${query}`;
	fetch(url).then((response)=>
	{
		return response.json();
	}).then((data)=>{
		
		//console.log(data);
		let text=`<div	class='list-group'>`;
		data.forEach((contact)=>
		{
			text+=`<a href='/users/${contact.contactId}/contacts' class='list-group-item list-group-action'>${contact.contactName} </a>`
		});
		text+= `</div>`;
		
		$(".search-result").html(text);
		$(".search-result").show();
		
		
	});
	
}
};