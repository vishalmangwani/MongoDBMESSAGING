package com.sample.project;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rabbitmq.client.Channel;
import com.sample.pack.LogginInUser;

@Controller
public class ApplicationController {
	private UserRepository userrepo;
	private Model model;
	public ApplicationController(UserRepository userrepo) {
		this.userrepo = userrepo;
	}
	
	@GetMapping("/")
	public String index(){
		return "index";
	}
	
	
	@GetMapping("/LoginUser")
	public void ValidateUser()
	{
		System.out.println("done");
	}
	
	
	@GetMapping("/getInActiveUsers")
	public List<LogginInUser> InActive() {
		System.out.println("Came into bean");		
		List<LogginInUser> users_return=new ArrayList<LogginInUser>();
		List<LogginInUser> users=this.userrepo.findAll();
		for(LogginInUser s:users){	
			if(s.isLoggedIn()==false)
				users_return.add(s);
		}
		return users_return;
	}
	@GetMapping("/getActiveUsers")
	public List<LogginInUser> Active() {
		System.out.println("Came into bean");	
		List<LogginInUser> users_return=new ArrayList<LogginInUser>();
		List<LogginInUser> users=this.userrepo.findAll();
		for(LogginInUser s:users){	
			if(s.isLoggedIn()==true)
				users_return.add(s);
		}
		return users_return;
	}
	@PostMapping("/addUser")
	public String insertNewUser(LogginInUser us, Model model) throws Exception{
		this.userrepo.insert(us);
		model.addAttribute("user",us);
		RabbitMQConn.connectAndBuildExchange(us.getUsername());
		return "index";	
	}
	@PostMapping("/updateUser")
	public String updateUser(LogginInUser us, Model model) throws Exception{
		LogginInUser us1=this.userrepo.findByUsername(us.getUsername());
		if(us1!=null && us.getPassword()!=null && us.getPassword().equals(us1.getPassword()) && us1.isLoggedIn()==false)
		{	
			this.userrepo.deleteByUsername(us1.getUsername());
			us1.setLoggedIn(true);
			this.userrepo.save(us1);
			this.model=model;
			List<LogginInUser> usernames = this.userrepo.findByUsernameNotIn(us1.getUsername());
			RabbitMQConn.connectAndBuildExchange(us1.getUsername());
			System.out.println(us.getUsername());
			this.model.addAttribute("LoggedInUser",us);
			this.model.addAttribute("users",usernames);
			return "HomePage";
		}
		else
			return "index";
		
	}
	@PostMapping("/logout")
	public String logout(Model model){
		LogginInUser us=(LogginInUser)this.model.asMap().get("LoggedInUser");
		if(us!=null){
			this.userrepo.deleteByUsername(us.getUsername());
			us.setLoggedIn(false);
			this.userrepo.save(us);
			model.addAttribute("LoggedInUser",us);
			return "index";
		}
		else
			return "failed";
	}
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable("id") String id){
		this.userrepo.deleteByUsername(id);
		return "Success";
	}
	@RequestMapping(value="/Messaging", method=RequestMethod.POST)
	public String MessageHomePage(@RequestParam("sendMessageTo") String sendo) throws Exception{
		boolean status=RabbitMQConn.createQueue(sendo, ((LogginInUser)this.model.asMap().get("LoggedInUser")).getUsername());
			if(RabbitMQConn.getAllMessage(sendo)!=null){
				byte[] message=RabbitMQConn.getAllMessage(sendo);
				List<String> msg=RabbitMQConn.readAllMessages(sendo);
				model.addAttribute("usermessages", msg);
				return "MessagesPage";
			}else
				return "NewMessage";
		}
	@RequestMapping(value="/PostMessage", method=RequestMethod.POST)
	public String PostMessage(@RequestParam("message") String message) throws Exception{
		RabbitMQConn.postMessage(message,((LogginInUser)this.model.asMap().get("LoggedInUser")).getUsername());
		return "MessagesPage";
	}
	
	
	
}
