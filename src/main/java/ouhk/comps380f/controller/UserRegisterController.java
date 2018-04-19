package ouhk.comps380f.controller;

import java.io.IOException;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.dao.TicketUserRepository;
import ouhk.comps380f.model.TicketUser;

@Controller
public class UserRegisterController {
    @Resource
    TicketUserRepository ticketUserRepo;
    
    public static class RegisterForm {
        private String username;
        private String password;
        private String pwAgain;
        private String[] role;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPwAgain() {
            return pwAgain;
        }

        public void setPwAgain(String pwAgain) {
            this.pwAgain = pwAgain;
        }

        public String[] getRole() {
            return role;
        }

        public void setRole(String[] role) {
            this.role = role;
        }
    }
    
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public ModelAndView register() {
        return new ModelAndView("register", "userRegister", new RegisterForm());
    }
    
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public View register(RegisterForm form) throws IOException {
        TicketUser user = new TicketUser(form.getUsername(),
                form.getPassword(),
                form.getRole()
        );
        ticketUserRepo.save(user);
        return new RedirectView("/login", true);
    }
}
