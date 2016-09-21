import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jakefroeb on 9/20/16.
 */
public class Main {
    static Message message;
    static User user;

    public static void main(String[] args) {

        ArrayList<Message> messages = new ArrayList();



        Spark.get("/", ((request, response) -> {
            HashMap m = new HashMap();

                    if (user == null) {
                        return new ModelAndView(m, "index.html");
                    } else {
                        m.put("name", user.userName);
                        m.put("messages", messages);
                        return new ModelAndView(m, "messages.html");

                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post("/create-user",
                ((request, response) -> {

                    String name = request.queryParams("userName");
                    user = new User(name);
                    response.redirect("/");

                    return "";
                })

        );

        Spark.post("/create-message",
                ((request, response) -> {
                    String messageText = request.queryParams("message");
                    message = new Message(messageText);
                    messages.add(message);
                    response.redirect("/");

                    return "";
                })

        );


    }

}
