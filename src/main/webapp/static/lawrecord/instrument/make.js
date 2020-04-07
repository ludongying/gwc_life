function one()  
    {    
		var E_D=Number(document.getElementById("E_D").value)
		var E_F=Number(document.getElementById("E_F").value)
		var N_D=Number(document.getElementById("N_D").value)
		var N_F=Number(document.getElementById("N_F").value)
		E_D = E_D*2
		if (E_F>29){E_D=E_D+1;E_F-=30;}
		N_D = N_D*2
		if (N_F>29){N_D=N_D+1;N_F-=30;}
		var small=(2-parseInt(N_F/10))*3+parseInt(E_F/10)+1;
		document.getElementById("FISHING_AREAS").value= "请核对坐标";
		if (E_F<30 && N_F<30)
		{
			if (N_D < 66 ){}
			else if (N_D < 67 )
			{
				if (E_D<241) {}
				else if (E_D<250){document.getElementById("FISHING_AREAS").value= E_D - 105 }	
			}
			else if (N_D < 68 )
			{
				if (E_D<241) {}
				else if (E_D<250){document.getElementById("FISHING_AREAS").value= E_D - 114 }	
			}
			else if (N_D < 69 )
			{
				if (E_D<240) {}
				else if (E_D<248){document.getElementById("FISHING_AREAS").value= E_D - 121 }	
			}
			else if (N_D < 70 )
			{
				if (E_D<238) {}
				else if (E_D<248){document.getElementById("FISHING_AREAS").value= E_D - 129 }	
			}
			else if (N_D < 71 )
			{
				if (E_D<238) {}
				else if (E_D<248){document.getElementById("FISHING_AREAS").value= E_D - 139 }	
			}
			else if (N_D < 72 )
			{	if (E_D<239) {}
				else if (E_D<248){document.getElementById("FISHING_AREAS").value= E_D - 149 }
			}
			if (document.getElementById("FISHING_AREAS").value != "请核对坐标")
			{
				document.getElementById("FISHING_AREAS").value += "/";
				document.getElementById("FISHING_AREAS").value += small;
			}
		}
		document.getElementById("Location").value = "("+document.getElementById("FISHING_AREAS").value+"渔区）"+"N"+document.getElementById("N_D").value+"°"+document.getElementById("N_F").value+"′,E"+document.getElementById("E_D").value+"°"+document.getElementById("E_F").value+"′";
    } 
	function Litigant1()
	{
		undefined_('Litigant');
		if(document.getElementById("Litigant").value == "公民")
		{
			document.getElementById("Natural1").style.display = "";
			document.getElementById("Natural2").style.display = "";
			document.getElementById("Name_1").value=document.getElementById("Ask_Name").value
			document.getElementById("Sex_1").value=document.getElementById("Ask_Sex").value
			document.getElementById("Age_1").value=document.getElementById("Ask_Age").value
			document.getElementById("Address_1").value=document.getElementById("Ask_Address").value
			document.getElementById("Phone_1").value=document.getElementById("Ask_Phone").value
			document.getElementById("ID_1").value=document.getElementById("Ask_ID").value
			document.getElementById("Unit1").style.display = "none";
			document.getElementById("Unit2").style.display = "none";
		}
		else if(document.getElementById("Litigant").value == "法人")
		{
			document.getElementById("Natural1").style.display = "none";
			document.getElementById("Natural2").style.display = "none";
			document.getElementById("Unit1").style.display = "";
			document.getElementById("Unit2").style.display = "";
			document.getElementById("Name_Unit").value=document.getElementById("Boat_Id").value
			document.getElementById("Legal").value=document.getElementById("Ask_Name").value
			document.getElementById("Phone_Unit").value=document.getElementById("Ask_Phone").value
			document.getElementById("Address_Unit").value=document.getElementById("Ask_Address").value
		}
		else
		{
			document.getElementById("Natural1").style.display = "none";
			document.getElementById("Natural2").style.display = "none";
			document.getElementById("Unit1").style.display = "none";
			document.getElementById("Unit2").style.display = "none";
		}
	}
	function YEAR_()
	{
		document.getElementById("YEAR").value=document.getElementById("Shou_year").value;
	}
	function Holder()
	{
		document.getElementById("Name_1").value = document.getElementById("Ask_Name").value;
		if(document.getElementById("Holder1").value == document.getElementById("Ask_Name").value)
		{
			document.getElementById("Holder2").value = "我";
		}
		else
		{
			document.getElementById("Holder2").value = document.getElementById("Holder1").value;
		}
	}
	function Hearing_()
	{
		undefined_('Hearing');
		if(document.getElementById("Hearing").value =="是")
		{
			document.getElementById("Hearing1").value = "（或要求听证），";
			document.getElementById("Hearing2").value = "（或不要求听证）的，";
		}
		else if(document.getElementById("Hearing").value =="否")
		{
			document.getElementById("Hearing1").value = "，";
			document.getElementById("Hearing2").value = "的，";
		}
		else
		{
			document.getElementById("Hearing1").value = "";
			document.getElementById("Hearing2").value = "";
		}
	}
	function undefined_($id)
	{
		if (document.getElementById($id).value == "")
			document.getElementById($id).style.color = 'red';
		else
			document.getElementById($id).style.color = 'black';
		if ($id == 'Type1')
		{
			document.getElementById('Type').value = document.getElementById($id).value ;
			if (document.getElementById($id).value == '捕捞辅助' || document.getElementById($id).value == '水产品收购')
			{
				document.getElementById('Net').type="hidden";
				document.getElementById('Net').value = '没有携带网具';
				document.getElementById('Net1').value = ' ';
				document.getElementById('Type').type = 'hidden' ;
			}
			else
			{
				document.getElementById('Net').type="text";
				document.getElementById('Net').value = ' ';
				if (document.getElementById($id).value == '其他' )
					document.getElementById('Type').type = 'text' ;
				else document.getElementById('Type').type = 'hidden' ;
			}
		}
		else if ($id == 'Type_paperwork1')
		{
			document.getElementById('Type_paperwork').value = document.getElementById($id).value ;
				if (document.getElementById($id).value == '其他' )
					document.getElementById('Type_paperwork').type = 'text' ;
				else document.getElementById('Type_paperwork').type = 'hidden' ;			
		}
		else if ($id == 'Ask_Position')
		{
			if (document.getElementById($id).value == '船长' )
			{
				document.getElementById('Captain').value = document.getElementById('Ask_Name').value;
			}
		}
		else if ($id == 'A002')
		{
			if (document.getElementById($id).value == '未正确涂写' || document.getElementById($id).value == "" )
			{
				document.getElementById('A0021').value = '，';
			}
			else
			{
				document.getElementById('A0021').value = '，是' + document.getElementById('Boat_Id').value +'，';
			}
		}
		else if ($id == 'A003')
		{
			if (document.getElementById($id).value == '未正确涂写' || document.getElementById($id).value == "" )
			{
				document.getElementById('A0031').value = '，';
			}
			else
			{
				document.getElementById('A0031').value = '，是' + document.getElementById('c25').value +'，';
			}
		}
		else if ($id == 'A004')
		{
			if (document.getElementById($id).value == '未正确悬挂' || document.getElementById($id).value == "" )
			{
				document.getElementById('A0041').value = '。';
			}
			else
			{
				document.getElementById('A0041').value = '，是' + document.getElementById('Boat_Id').value +'。';
			}
		}
	}
	function qianwa_()
	{
		document.getElementById("mali").value = Math.round(document.getElementById("qianwa").value*136)/100;
	}
	function mali_()
	{
		document.getElementById("qianwa").value = Math.round(document.getElementById("mali").value/1.36*100)/100;
	}
	
	function Carry_()
	{
		undefined_('Carry');
		if(document.getElementById("Carry").value =="")
		{
			document.getElementById("License1").value = "";
			document.getElementById("License2").value = "";
			document.getElementById("License3").value = "";
			document.getElementById("License4").value = "";
		}
		if(document.getElementById("Carry").value =="no")
		{
			document.getElementById("License1").value = "没有出示。";
			document.getElementById("License2").value = "未能出示";
			document.getElementById("License3").value = "未携带捕捞许可证，从事";
			document.getElementById("License4").value = "有，忘记带了";
			document.getElementById("License5").value = "未能出示捕捞许可证";
			document.getElementById("IF_License5").style.display = "none";
		}
		else
		{
			document.getElementById("License1").value = "出示了。";
			document.getElementById("License2").value = "出示了";
			document.getElementById("License3").value = "从事";
			document.getElementById("License4").value = "有";
			document.getElementById("License5").value = "出示了捕捞许可证,编号是，船号是，主机功率是，作业方式是，持证人是";
			document.getElementById("IF_License5").style.display = "";
		}
	}
	
	function Type_Violation_()
	{
		undefined_('Type_Violation1');
		document.getElementById("Type_Violation").value = document.getElementById("Type_Violation1").value;
		if(document.getElementById("Type_Violation1").value =="使用炸鱼的方法进行捕捞")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第三十条第一款，“禁止使用炸鱼、毒鱼、电鱼等破坏渔业资源的方法进行捕捞。禁止制造、销售、使用禁用的渔具。禁止在禁渔区、禁渔期进行捕捞。禁止使用小于最小网目尺寸的网具进行捕捞。捕捞的渔获物中幼鱼不得超过规定的比例。在禁渔区或者禁渔期内禁止销售非法捕捞的渔获物。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第三十八条第一款，“使用炸鱼、毒鱼、电鱼等破坏渔业资源方法进行捕捞的，违反关于禁渔区、禁渔期的规定进行捕捞的，或者使用禁用的渔具、捕捞方法和小于最小网目尺寸的网具进行捕捞或者渔获物中幼鱼超过规定比例的，没收渔获物和违法所得，处五万元以下的罚款；情节严重的，没收渔具，吊销捕捞许可证；情节特别严重的，可以没收渔船；构成犯罪的，依法追究刑事责任。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第三十条第一款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第三十八条第一款";
		}
		else if(document.getElementById("Type_Violation1").value =="使用毒鱼的方法进行捕捞")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第三十条第一款，“禁止使用炸鱼、毒鱼、电鱼等破坏渔业资源的方法进行捕捞。禁止制造、销售、使用禁用的渔具。禁止在禁渔区、禁渔期进行捕捞。禁止使用小于最小网目尺寸的网具进行捕捞。捕捞的渔获物中幼鱼不得超过规定的比例。在禁渔区或者禁渔期内禁止销售非法捕捞的渔获物。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第三十八条第一款，“使用炸鱼、毒鱼、电鱼等破坏渔业资源方法进行捕捞的，违反关于禁渔区、禁渔期的规定进行捕捞的，或者使用禁用的渔具、捕捞方法和小于最小网目尺寸的网具进行捕捞或者渔获物中幼鱼超过规定比例的，没收渔获物和违法所得，处五万元以下的罚款；情节严重的，没收渔具，吊销捕捞许可证；情节特别严重的，可以没收渔船；构成犯罪的，依法追究刑事责任。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第三十条第一款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第三十八条第一款";
		}
		else if(document.getElementById("Type_Violation1").value =="使用电鱼的方法进行捕捞")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第三十条第一款，“禁止使用炸鱼、毒鱼、电鱼等破坏渔业资源的方法进行捕捞。禁止制造、销售、使用禁用的渔具。禁止在禁渔区、禁渔期进行捕捞。禁止使用小于最小网目尺寸的网具进行捕捞。捕捞的渔获物中幼鱼不得超过规定的比例。在禁渔区或者禁渔期内禁止销售非法捕捞的渔获物。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第三十八条第一款，“使用炸鱼、毒鱼、电鱼等破坏渔业资源方法进行捕捞的，违反关于禁渔区、禁渔期的规定进行捕捞的，或者使用禁用的渔具、捕捞方法和小于最小网目尺寸的网具进行捕捞或者渔获物中幼鱼超过规定比例的，没收渔获物和违法所得，处五万元以下的罚款；情节严重的，没收渔具，吊销捕捞许可证；情节特别严重的，可以没收渔船；构成犯罪的，依法追究刑事责任。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第三十条第一款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第三十八条第一款";
		}
		else if(document.getElementById("Type_Violation1").value =="违反禁渔区的规定进行捕捞")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第三十条第一款，“禁止使用炸鱼、毒鱼、电鱼等破坏渔业资源的方法进行捕捞。禁止制造、销售、使用禁用的渔具。禁止在禁渔区、禁渔期进行捕捞。禁止使用小于最小网目尺寸的网具进行捕捞。捕捞的渔获物中幼鱼不得超过规定的比例。在禁渔区或者禁渔期内禁止销售非法捕捞的渔获物。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第三十八条第一款，“使用炸鱼、毒鱼、电鱼等破坏渔业资源方法进行捕捞的，违反关于禁渔区、禁渔期的规定进行捕捞的，或者使用禁用的渔具、捕捞方法和小于最小网目尺寸的网具进行捕捞或者渔获物中幼鱼超过规定比例的，没收渔获物和违法所得，处五万元以下的罚款；情节严重的，没收渔具，吊销捕捞许可证；情节特别严重的，可以没收渔船；构成犯罪的，依法追究刑事责任。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第三十条第一款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第三十八条第一款";
		}
		else if(document.getElementById("Type_Violation1").value =="违反禁渔期的规定进行捕捞")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第三十条第一款，“禁止使用炸鱼、毒鱼、电鱼等破坏渔业资源的方法进行捕捞。禁止制造、销售、使用禁用的渔具。禁止在禁渔区、禁渔期进行捕捞。禁止使用小于最小网目尺寸的网具进行捕捞。捕捞的渔获物中幼鱼不得超过规定的比例。在禁渔区或者禁渔期内禁止销售非法捕捞的渔获物。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第三十八条第一款，“使用炸鱼、毒鱼、电鱼等破坏渔业资源方法进行捕捞的，违反关于禁渔区、禁渔期的规定进行捕捞的，或者使用禁用的渔具、捕捞方法和小于最小网目尺寸的网具进行捕捞或者渔获物中幼鱼超过规定比例的，没收渔获物和违法所得，处五万元以下的罚款；情节严重的，没收渔具，吊销捕捞许可证；情节特别严重的，可以没收渔船；构成犯罪的，依法追究刑事责任。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第三十条第一款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第三十八条第一款";
		}
		else if(document.getElementById("Type_Violation1").value =="使用禁用的渔具进行捕捞")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第三十条第一款，“禁止使用炸鱼、毒鱼、电鱼等破坏渔业资源的方法进行捕捞。禁止制造、销售、使用禁用的渔具。禁止在禁渔区、禁渔期进行捕捞。禁止使用小于最小网目尺寸的网具进行捕捞。捕捞的渔获物中幼鱼不得超过规定的比例。在禁渔区或者禁渔期内禁止销售非法捕捞的渔获物。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第三十八条第一款，“使用炸鱼、毒鱼、电鱼等破坏渔业资源方法进行捕捞的，违反关于禁渔区、禁渔期的规定进行捕捞的，或者使用禁用的渔具、捕捞方法和小于最小网目尺寸的网具进行捕捞或者渔获物中幼鱼超过规定比例的，没收渔获物和违法所得，处五万元以下的罚款；情节严重的，没收渔具，吊销捕捞许可证；情节特别严重的，可以没收渔船；构成犯罪的，依法追究刑事责任。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第三十条第一款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第三十八条第一款";
		}
		else if(document.getElementById("Type_Violation1").value =="使用禁用的捕捞方法进行捕捞")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第三十条第一款，“禁止使用炸鱼、毒鱼、电鱼等破坏渔业资源的方法进行捕捞。禁止制造、销售、使用禁用的渔具。禁止在禁渔区、禁渔期进行捕捞。禁止使用小于最小网目尺寸的网具进行捕捞。捕捞的渔获物中幼鱼不得超过规定的比例。在禁渔区或者禁渔期内禁止销售非法捕捞的渔获物。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第三十八条第一款，“使用炸鱼、毒鱼、电鱼等破坏渔业资源方法进行捕捞的，违反关于禁渔区、禁渔期的规定进行捕捞的，或者使用禁用的渔具、捕捞方法和小于最小网目尺寸的网具进行捕捞或者渔获物中幼鱼超过规定比例的，没收渔获物和违法所得，处五万元以下的罚款；情节严重的，没收渔具，吊销捕捞许可证；情节特别严重的，可以没收渔船；构成犯罪的，依法追究刑事责任。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第三十条第一款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第三十八条第一款";
		}
		else if(document.getElementById("Type_Violation1").value =="使用小于最小网目尺寸的网具进行捕捞")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第三十条第一款，“禁止使用炸鱼、毒鱼、电鱼等破坏渔业资源的方法进行捕捞。禁止制造、销售、使用禁用的渔具。禁止在禁渔区、禁渔期进行捕捞。禁止使用小于最小网目尺寸的网具进行捕捞。捕捞的渔获物中幼鱼不得超过规定的比例。在禁渔区或者禁渔期内禁止销售非法捕捞的渔获物。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第三十八条第一款，“使用炸鱼、毒鱼、电鱼等破坏渔业资源方法进行捕捞的，违反关于禁渔区、禁渔期的规定进行捕捞的，或者使用禁用的渔具、捕捞方法和小于最小网目尺寸的网具进行捕捞或者渔获物中幼鱼超过规定比例的，没收渔获物和违法所得，处五万元以下的罚款；情节严重的，没收渔具，吊销捕捞许可证；情节特别严重的，可以没收渔船；构成犯罪的，依法追究刑事责任。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第三十条第一款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第三十八条第一款";
		}
		else if(document.getElementById("Type_Violation1").value =="捕捞的渔获物中幼鱼超过规定比例")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第三十条第一款，“禁止使用炸鱼、毒鱼、电鱼等破坏渔业资源的方法进行捕捞。禁止制造、销售、使用禁用的渔具。禁止在禁渔区、禁渔期进行捕捞。禁止使用小于最小网目尺寸的网具进行捕捞。捕捞的渔获物中幼鱼不得超过规定的比例。在禁渔区或者禁渔期内禁止销售非法捕捞的渔获物。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第三十八条第一款，“使用炸鱼、毒鱼、电鱼等破坏渔业资源方法进行捕捞的，违反关于禁渔区、禁渔期的规定进行捕捞的，或者使用禁用的渔具、捕捞方法和小于最小网目尺寸的网具进行捕捞或者渔获物中幼鱼超过规定比例的，没收渔获物和违法所得，处五万元以下的罚款；情节严重的，没收渔具，吊销捕捞许可证；情节特别严重的，可以没收渔船；构成犯罪的，依法追究刑事责任。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第三十条第一款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第三十八条第一款";
		}
		else if(document.getElementById("Type_Violation1").value =="在禁渔区销售非法捕捞的渔获物")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第三十条第一款，“禁止使用炸鱼、毒鱼、电鱼等破坏渔业资源的方法进行捕捞。禁止制造、销售、使用禁用的渔具。禁止在禁渔区、禁渔期进行捕捞。禁止使用小于最小网目尺寸的网具进行捕捞。捕捞的渔获物中幼鱼不得超过规定的比例。在禁渔区或者禁渔期内禁止销售非法捕捞的渔获物。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第三十八条第二款，“在禁渔区或者禁渔期内销售非法捕捞的渔获物的，县级以上地方人民政府渔业行政主管部门应当及时进行调查处理。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第三十条第一款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第三十八条第二款";
		}
		else if(document.getElementById("Type_Violation1").value =="在禁渔期内销售非法捕捞的渔获物")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第三十条第一款，“禁止使用炸鱼、毒鱼、电鱼等破坏渔业资源的方法进行捕捞。禁止制造、销售、使用禁用的渔具。禁止在禁渔区、禁渔期进行捕捞。禁止使用小于最小网目尺寸的网具进行捕捞。捕捞的渔获物中幼鱼不得超过规定的比例。在禁渔区或者禁渔期内禁止销售非法捕捞的渔获物。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第三十八条第二款，“在禁渔区或者禁渔期内销售非法捕捞的渔获物的，县级以上地方人民政府渔业行政主管部门应当及时进行调查处理。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第三十条第一款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第三十八条第二款";
		}
		else if(document.getElementById("Type_Violation1").value =="制造禁用的渔具")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第三十条第一款，“禁止使用炸鱼、毒鱼、电鱼等破坏渔业资源的方法进行捕捞。禁止制造、销售、使用禁用的渔具。禁止在禁渔区、禁渔期进行捕捞。禁止使用小于最小网目尺寸的网具进行捕捞。捕捞的渔获物中幼鱼不得超过规定的比例。在禁渔区或者禁渔期内禁止销售非法捕捞的渔获物。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第三十八条第三款，“制造、销售禁用的渔具的，没收非法制造、销售的渔具和违法所得，并处一万元以下的罚款。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第三十条第一款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第三十八条第三款";
		}
		else if(document.getElementById("Type_Violation1").value =="销售禁用的渔具")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第三十条第一款，“禁止使用炸鱼、毒鱼、电鱼等破坏渔业资源的方法进行捕捞。禁止制造、销售、使用禁用的渔具。禁止在禁渔区、禁渔期进行捕捞。禁止使用小于最小网目尺寸的网具进行捕捞。捕捞的渔获物中幼鱼不得超过规定的比例。在禁渔区或者禁渔期内禁止销售非法捕捞的渔获物。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第三十八条第三款，“制造、销售禁用的渔具的，没收非法制造、销售的渔具和违法所得，并处一万元以下的罚款。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第三十条第一款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第三十八条第三款";
		}
		else if(document.getElementById("Type_Violation1").value =="未经批准在水产种质资源保护区从事捕捞活动")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第二十九条，“国家保护水产种质资源及其生存环境，并在具有较高经济价值和遗传育种价值的水产种质资源的主要生长繁育区域建立水产种质资源保护区。未经国务院渔业行政主管部门批准，任何单位或者个人不得在水产种质资源保护区内从事捕捞活动。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第四十五条，“未经批准在水产种质资源保护区内从事捕捞活动的，责令立即停止捕捞，没收渔获物和渔具，可以并处一万元以下的罚款。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第二十九条";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第四十五条";
		}
		else if(document.getElementById("Type_Violation1").value =="未依法取得捕捞许可证进行捕捞1")//罚款
		{
			document.getElementById("Type_Violation").value = "未依法取得捕捞许可证进行捕捞";
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第二十三条第一款，“国家对捕捞业实行捕捞许可证制度。”、《渔业捕捞许可管理规定》第十六条第二款，“渔业捕捞许可证必须随船携带（徒手作业的必须随身携带），妥善保管，并接受渔业行政执法人员的检查。”、第三十五条第三款，“使用无效的渔业捕捞许可证，或未携带渔业捕捞许可证从事渔业捕捞活动的为无证捕捞。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第四十一条，“未依法取得捕捞许可证擅自进行捕捞的，没收渔获物和违法所得，并处十万元以下的罚款；情节严重的，并可以没收渔具和渔船。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第二十三条第一款、《渔业捕捞许可管理规定》第十六条第二款、第三十五条第三款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第四十一条";
		}
		else if(document.getElementById("Type_Violation1").value =="未依法取得捕捞许可证进行捕捞2")//资源赔偿费
		{
			document.getElementById("Type_Violation").value = "未依法取得捕捞许可证进行捕捞";
			document.getElementById("Laws_Violation2").value = "《江苏省渔业管理条例》第十八条，“从事捕捞业的单位和个人，应当向县级以上地方人民政府渔业行政主管部门申领由国务院渔业行政主管部门统一监制的捕捞许可证。”";
			document.getElementById("Laws_Basis2").value = "《江苏省渔业管理条例》第四十条，“违反本条例规定，造成国家渔业资源损失的，渔业资源损失的赔偿，按照渔业生物致死量的零点五到三倍计算。”";
			document.getElementById("Laws_Violation").value = "《江苏省渔业管理条例》第十八条";
			document.getElementById("Laws_Basis").value = "《江苏省渔业管理条例》第四十条";
		}
		else if(document.getElementById("Type_Violation1").value =="未依法取得捕捞许可证进行捕捞3")//罚款+资源赔偿费
		{
			document.getElementById("Type_Violation").value = "未依法取得捕捞许可证进行捕捞";
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第二十三条第一款，“国家对捕捞业实行捕捞许可证制度。”、《渔业捕捞许可管理规定》第十六条第二款，“渔业捕捞许可证必须随船携带（徒手作业的必须随身携带），妥善保管，并接受渔业行政执法人员的检查。”、第三十五条第三款，“使用无效的渔业捕捞许可证，或未携带渔业捕捞许可证从事渔业捕捞活动的为无证捕捞。”《江苏省渔业管理条例》第十八条，“从事捕捞业的单位和个人，应当向县级以上地方人民政府渔业行政主管部门申领由国务院渔业行政主管部门统一监制的捕捞许可证。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第四十一条，“未依法取得捕捞许可证擅自进行捕捞的，没收渔获物和违法所得，并处十万元以下的罚款；情节严重的，并可以没收渔具和渔船。”及《江苏省渔业管理条例》第四十条，“违反本条例规定，造成国家渔业资源损失的，渔业资源损失的赔偿，按照渔业生物致死量的零点五到三倍计算。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第二十三条第一款、《渔业捕捞许可管理规定》第十六条第二款、第三十五条第三款及《江苏省渔业管理条例》第十八条";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第四十一条及《江苏省渔业管理条例》第四十条";
		}
		else if(document.getElementById("Type_Violation1").value =="违反捕捞许可证关于作业类型规定进行捕捞")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第二十五条，“从事捕捞作业的单位和个人，必须按照捕捞许可证关于作业类型、场所、时限、渔具数量和捕捞限额的规定进行作业，并遵守国家有关保护渔业资源的规定，大中型渔船应当填写渔捞日志。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第四十二条，“违反捕捞许可证关于作业类型、场所、时限和渔具数量的规定进行捕捞的，没收渔获物和违法所得，可以并处五万元以下的罚款；情节严重的，并可以没收渔具，吊销捕捞许可证。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第二十五条";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第四十二条";
		}
		else if(document.getElementById("Type_Violation1").value =="违反捕捞许可证关于作业场所规定进行捕捞")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第二十五条，“从事捕捞作业的单位和个人，必须按照捕捞许可证关于作业类型、场所、时限、渔具数量和捕捞限额的规定进行作业，并遵守国家有关保护渔业资源的规定，大中型渔船应当填写渔捞日志。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第四十二条，“违反捕捞许可证关于作业类型、场所、时限和渔具数量的规定进行捕捞的，没收渔获物和违法所得，可以并处五万元以下的罚款；情节严重的，并可以没收渔具，吊销捕捞许可证。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第二十五条";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第四十二条";
		}
		else if(document.getElementById("Type_Violation1").value =="违反捕捞许可证关于作业时限规定进行捕捞")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第二十五条，“从事捕捞作业的单位和个人，必须按照捕捞许可证关于作业类型、场所、时限、渔具数量和捕捞限额的规定进行作业，并遵守国家有关保护渔业资源的规定，大中型渔船应当填写渔捞日志。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第四十二条，“违反捕捞许可证关于作业类型、场所、时限和渔具数量的规定进行捕捞的，没收渔获物和违法所得，可以并处五万元以下的罚款；情节严重的，并可以没收渔具，吊销捕捞许可证。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第二十五条";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第四十二条";
		}
		else if(document.getElementById("Type_Violation1").value =="违反捕捞许可证关于渔具数量规定进行捕捞")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第二十五条，“从事捕捞作业的单位和个人，必须按照捕捞许可证关于作业类型、场所、时限、渔具数量和捕捞限额的规定进行作业，并遵守国家有关保护渔业资源的规定，大中型渔船应当填写渔捞日志。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第四十二条，“违反捕捞许可证关于作业类型、场所、时限和渔具数量的规定进行捕捞的，没收渔获物和违法所得，可以并处五万元以下的罚款；情节严重的，并可以没收渔具，吊销捕捞许可证。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第二十五条";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第四十二条";
		}
		else if(document.getElementById("Type_Violation1").value =="涂改、买卖、出租或者以其他形式转让捕捞许可证")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第二十三条第三款，“捕捞许可证不得买卖、出租和以其他形式转让，不得涂改、伪造、变造。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第四十三条，“涂改、买卖、出租或者以其他形式转让捕捞许可证的，没收违法所得，吊销捕捞许可证，可以并处一万元以下的罚款；伪造、变造、买卖捕捞许可证，构成犯罪的，依法追究刑事责任。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第二十三条第三款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第四十三条";
		}
		else if(document.getElementById("Type_Violation1").value =="伪造、变造、买卖捕捞许可证")
		{
			document.getElementById("Laws_Violation2").value = "《中华人民共和国渔业法》第二十三条第三款，“捕捞许可证不得买卖、出租和以其他形式转让，不得涂改、伪造、变造。”";
			document.getElementById("Laws_Basis2").value = "《中华人民共和国渔业法》第四十三条，“涂改、买卖、出租或者以其他形式转让捕捞许可证的，没收违法所得，吊销捕捞许可证，可以并处一万元以下的罚款；伪造、变造、买卖捕捞许可证，构成犯罪的，依法追究刑事责任。”";
			document.getElementById("Laws_Violation").value = "《中华人民共和国渔业法》第二十三条第三款";
			document.getElementById("Laws_Basis").value = "《中华人民共和国渔业法》第四十三条";
		}
		else 
		{
			document.getElementById("Laws_Violation2").value = "";
			document.getElementById("Laws_Basis2").value = "";
			document.getElementById("Laws_Violation").value = "";
			document.getElementById("Laws_Basis").value = "";
		}
	}
	

	function time()
	{
		var d = new Date();
		var YY = document.getElementById("Fa_year").value;
		var MM=document.getElementById("Fa_Month").value -1;
		var DD=document.getElementById("Fa_Day").value;
		var H=document.getElementById("Fa_Hour").value;
		var M=document.getElementById("Fa_Minute").value;
		d.setFullYear( YY, MM, DD);
		d.setHours(H,M );
		
		document.getElementById("Time").value=d.getFullYear() + "年" + checkTime(d.getMonth()+1) + "月" + checkTime(d.getDate()) + "日";
		document.getElementById("Time2").value=d.getFullYear() + "年" + checkTime(d.getMonth()+1) + "月" + checkTime(d.getDate()) + "日";
		document.getElementById("Time3").value=d.getFullYear() + "年" + checkTime(d.getMonth()+1) + "月" + checkTime(d.getDate()) + "日";
		document.getElementById("Time4").value=d.getFullYear() + "年" + checkTime(d.getMonth()+1) + "月" + checkTime(d.getDate()) + "日";
		document.getElementById("Time5").value=d.getFullYear() + "年" + checkTime(d.getMonth()+1) + "月" + checkTime(d.getDate()) + "日";
		document.getElementById("Time6").value=d.getFullYear() + "年" + checkTime(d.getMonth()+1) + "月" + checkTime(d.getDate()) + "日";
		
		d.setMinutes(d.getMinutes()+20);	
		document.getElementById("Check_year").value= d.getFullYear();
		document.getElementById("Check_Month").value= checkTime(d.getMonth()+1);
		document.getElementById("Check_Day").value= checkTime(d.getDate());
		document.getElementById("Check_Hour1").value= checkTime(d.getHours());
		document.getElementById("Check_Minute1").value= checkTime(d.getMinutes());
		
		d.setMinutes(d.getMinutes()+8);
		document.getElementById("Check_Hour2").value= checkTime(d.getHours());
		document.getElementById("Check_Minute2").value= checkTime(d.getMinutes());
		
		d.setMinutes(d.getMinutes()+5);	
		document.getElementById("Ask_year").value= d.getFullYear();
		document.getElementById("Ask_Month").value= checkTime(d.getMonth()+1);
		document.getElementById("Ask_Day").value= checkTime(d.getDate());
		document.getElementById("Ask_Hour1").value= checkTime(d.getHours());
		document.getElementById("Ask_Minute1").value= checkTime(d.getMinutes());
		
		d.setMinutes(d.getMinutes()+20);
		document.getElementById("Ask_Hour2").value= checkTime(d.getHours());
		document.getElementById("Ask_Minute2").value= checkTime(d.getMinutes());
		
		d.setMinutes(d.getMinutes()+10);	
		document.getElementById("Phone_year").value= d.getFullYear();
		document.getElementById("Phone_Month").value= checkTime(d.getMonth()+1);
		document.getElementById("Phone_Day").value= checkTime(d.getDate());
		document.getElementById("Phone_Hour").value= checkTime(d.getHours());
		document.getElementById("Phone_Minute").value= checkTime(d.getMinutes());
		
		document.getElementById("Shou_year").value= d.getFullYear();
		document.getElementById("Shou_Month").value= checkTime(d.getMonth()+1);
		document.getElementById("Shou_Day").value= checkTime(d.getDate());
		document.getElementById("YEAR").value=document.getElementById("Shou_year").value;
		
		d.setMinutes(d.getMinutes()+60);
		document.getElementById("Idea_year").value= d.getFullYear();
		document.getElementById("Idea_Month").value= checkTime(d.getMonth()+1);
		document.getElementById("Idea_Day").value= checkTime(d.getDate());
		document.getElementById("Idea_Hour").value= checkTime(d.getHours());
		document.getElementById("Idea_Minute").value= checkTime(d.getMinutes());
		
		d.setMinutes(d.getMinutes()+60);
		document.getElementById("PunishAsk_year").value= d.getFullYear();
		document.getElementById("PunishAsk_Month").value= checkTime(d.getMonth()+1);
		document.getElementById("PunishAsk_Day").value= checkTime(d.getDate());
		document.getElementById("PunishAsk_Hour").value= checkTime(d.getHours());
		document.getElementById("PunishAsk_Minute").value= checkTime(d.getMinutes());
			
		document.getElementById("Punish_year").value= d.getFullYear();
		document.getElementById("Punish_Month").value= checkTime(d.getMonth()+1);
		document.getElementById("Punish_Day").value= checkTime(d.getDate());
		
		d.setDate(d.getDate()+185);	
		document.getElementById("Closed_year").value= d.getFullYear();
		document.getElementById("Closed_Month").value= checkTime(d.getMonth()+1);
		document.getElementById("Closed_Day").value= checkTime(d.getDate());
		
		d.setFullYear(d.getFullYear()+10);	
		d.setDate(d.getDate()-1);
		document.getElementById("Keep_year").value= d.getFullYear();
		document.getElementById("Keep_Month").value= checkTime(d.getMonth()+1);
		document.getElementById("Keep_Day").value= checkTime(d.getDate());
	}
	
	function checkTime(i)
	{
		if (i<10) 
		{
			i="0" + i;
		}
		return i;
	}
	function check_cfy()
	{
		if (document.getElementById("Number").value=="")
		{
			alert("请输入案件编号");
			return false;
		}

		if (document.getElementById("YEAR").value=="")
		{
			alert("请输入案件年份");
			return false;
		}

		if (document.getElementById("Boat_Id").value=="")
		{
			alert("请输入渔船船号");
			return false;
		}
	}
	
	function low_to_up($id)
	{
	
		$num=document.getElementById($id).value;
		var $f=new Array()
		$f[0]="零";
		$f[1]="壹";
		$f[2]="贰";
		$f[3]="叁";
		$f[4]="肆";
		$f[5]="伍";
		$f[6]="陆";
		$f[7]="柒";
		$f[8]="捌";
		$f[9]="玖";
		$ans="";$flag=0;
		if ($num.length>4)
		{
			$wan=$num.slice(0,-4);
			if ($wan.length>=4 && $wan.slice(-4,-3)>0)
			{
				$ans = $ans+$f[$wan.slice(-4,-3)]+"仟";
				if ($wan.slice(-3,-2)>0)
				{
					$ans+=$f[$wan.slice(-3,-2)]+"佰";
					if ($wan.slice(-2,-1)>0)
					{
						$ans+=$f[$wan.slice(-2,-1)]+"拾";
						if ($wan.slice(-1)>0)
						{
							$ans+=$f[$wan.slice(-1)];
						}
					}
					else
					{
						if ($wan.slice(-1)>0)
						{
							$ans+="零"+$f[$wan.slice(-1)];
						}
					}
				}
				else
				{
					if ($wan.slice(-2,-1)>0)
					{
						$ans+="零"+$f[$wan.slice(-2,-1)]+"拾";
						if ($wan.slice(-1)>0)
						{
							$ans+=$f[$wan.slice(-1)];
						}
					}
					else
					{
						if ($wan.slice(-1)>0)
						{
							$ans+="零"+$f[$wan.slice(-1)];
						}
					}					
				}
			}
			else
			{
				if ($wan.length>=3 && $wan.slice(-3,-2)>0)
				{
					$ans+=$f[$wan.slice(-3,-2)]+"佰";
					if ($wan.slice(-2,-1)>0)
					{
						$ans+=$f[$wan.slice(-2,-1)]+"拾";
						if ($wan.slice(-1)>0)
						{
							$ans+=$f[$wan.slice(-1)];
						}
					}
					else
					{
						if ($wan.slice(-1)>0)
						{
							$ans+="零"+$f[$wan.slice(-1)];
						}
					}
				}
				else
				{
					if ($wan.length>=2 && $wan.slice(-2,-1)>0)
					{
						$ans+=$f[$wan.slice(-2,-1)]+"拾";
						if ($wan.slice(-1)>0)
						{
							$ans+=$f[$wan.slice(-1)];
						}
					}
					else
					{
						if ($wan.slice(-1)>"0")
						{
							$ans+=$f[$wan.slice(-1)];
						}
					}					
				}
			}
			$ans+="万";
			$flag=1;
		}
		$num=$num.slice(-4);
		$wan=$num;
			if ($wan.length>=4 && $wan.slice(-4,-3)>0)
			{
				$ans = $ans+$f[$wan.slice(-4,-3)]+"仟";
				if ($wan.slice(-3,-2)>0)
				{
					$ans+=$f[$wan.slice(-3,-2)]+"佰";
					if ($wan.slice(-2,-1)>0)
					{
						$ans+=$f[$wan.slice(-2,-1)]+"拾";
						if ($wan.slice(-1)>0)
						{
							$ans+=$f[$wan.slice(-1)];
						}
					}
					else
					{
						if ($wan.slice(-1)>0)
						{
							$ans+="零"+$f[$wan.slice(-1)];
						}
					}
				}
				else
				{
					if ($wan.slice(-2,-1)>0)
					{
						$ans+="零"+$f[$wan.slice(-2,-1)]+"拾";
						if ($wan.slice(-1)>0)
						{
							$ans+=$f[$wan.slice(-1)];
						}
					}
					else
					{
						if ($wan.slice(-1)>0)
						{
							$ans+="零"+$f[$wan.slice(-1)];
						}
					}					
				}
			}
			else
			{
				if ($wan.length>=3 && $wan.slice(-3,-2)>0)
				{
					if ($flag==1)$ans+="零";
					$ans+=$f[$wan.slice(-3,-2)]+"佰";
					if ($wan.slice(-2,-1)>0)
					{
						$ans+=$f[$wan.slice(-2,-1)]+"拾";
						if ($wan.slice(-1)>0)
						{
							$ans+=$f[$wan.slice(-1)];
						}
					}
					else
					{
						if ($wan.slice(-1)>0)
						{
							$ans+="零"+$f[$wan.slice(-1)];
						}
					}
				}
				else
				{
					if ($wan.length>=2 && $wan.slice(-2,-1)>0)
					{
						if ($flag==1)$ans+="零";
						$ans+=$f[$wan.slice(-2,-1)]+"拾";
						if ($wan.slice(-1)>0)
						{
							$ans+=$f[$wan.slice(-1)];
						}
					}
					else
					{
						if ($wan.slice(-1)>0)
						{
							if ($flag==1)$ans+="零";
							$ans+=$f[$wan.slice(-1)];
						}
					}					
				}
			}
//			alert("ok");
			return $ans;
	}
	
	function money_()
	{
		if ( isNaN(parseInt(document.getElementById("money_low1").value))) document.getElementById("money_low").value=parseInt(document.getElementById("money_low2").value);
		else if ( isNaN(parseInt(document.getElementById("money_low2").value))) document.getElementById("money_low").value=parseInt(document.getElementById("money_low1").value);
		else
		document.getElementById("money_low").value=parseInt(document.getElementById("money_low1").value) + parseInt(document.getElementById("money_low2").value);
//		alert(document.getElementById("money_low").value );
		$money=low_to_up("money_low1");
		if ($money != "" && $money!="0")
			document.getElementById("money").value="罚款人民币"+$money+"元整。";
		else document.getElementById("money").value="";
		
		$money=low_to_up("money_low2");
		if ($money != "" && $money!="0")
			document.getElementById("money2").value="缴纳资源赔偿费人民币"+$money+"元整。";
		else document.getElementById("money2").value="";
		
		if (document.getElementById("money").value != "" && document.getElementById("money2").value != "")
		{
			document.getElementById("money").value="1、"+document.getElementById("money").value;
			document.getElementById("money2").value="2、"+document.getElementById("money2").value;
			document.getElementById("money1").value=document.getElementById("money").value+document.getElementById("money2").value;
		}
		else 
		{
			document.getElementById("money1").value="1、"+document.getElementById("money").value+document.getElementById("money2").value;
			document.getElementById("money").value=document.getElementById("money1").value;
			document.getElementById("money2").value=" ";
		}
	}
	
	function AskID()
	{

            var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
            var tip = "";
            var pass= true;
            
		code =document.getElementById("Ask_ID").value.toUpperCase();
                if(code.length != 18)
		{
                        tip = "位数错误";
                        pass =false;
		}
		else if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
                tip = "身份证号格式错误";
                pass = false;
            }
            
           else if(!city[code.substr(0,2)]){
                tip = "地址编码错误";
                pass = false;
            }
            else{
                //18位身份证需要验证最后一位校验位
                    code = code.split('');
                    //∑(ai×Wi)(mod 11)
                    //加权因子
                    var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
                    //校验位
                    var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
                    var sum = 0;
                    var ai = 0;
                    var wi = 0;
                    for (var i = 0; i < 17; i++)
                    {
                        ai = code[i];
                        wi = factor[i];
                        sum += ai * wi;
                    }
                    var last = parity[sum % 11];
                    if(parity[sum % 11] != code[17]){
                        tip = "校验位错误";
                        pass =false;
                    }
            }

            if(!pass)
		{	
			alert(tip);
		}
	}

	function Net_()
	{
		document.getElementById("Net1").value="8、该船共携带网具有"+document.getElementById('Net').value+"。";
	}
	
	function product()
	{
		document.getElementById("Product").value=document.getElementById('Product_name').value;
		document.getElementById("A005").value=document.getElementById('Product_name').value;
		if (document.getElementById('Product_price').value != "" && document.getElementById('Product_price').value != "0" && document.getElementById('Product_price').value != "0元" )
		{
			document.getElementById("Product").value += "，市场价值约"+document.getElementById('Product_price').value;
			document.getElementById("Lost").value = "造成约"+document.getElementById('Product_price').value+"的渔业损失，";
		}
	}
		
	