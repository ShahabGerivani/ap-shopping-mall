import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;

public class UsersList extends JPanel {
    UsersList(JFrame frame, Connection dbConnection, User user) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JPanel UpperPanel = new JPanel();
        JPanel lowerPanel = new JPanel();

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        AdminNavBar adminNavBar = new AdminNavBar(user.getBalance(), "خوش آمدید " + user.getUsername(), frame, dbConnection, user, this);
        UpperPanel.add(adminNavBar);
        this.add(adminNavBar, gridBagConstraints);

        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(30, 3, 10, 3);

        gbc.gridx = 0;
        gbc.gridy = 0;

        // just for test

        ArrayList<User> users = new ArrayList<>();
        User user1 = new User("gol", "ali", "09309547459", "خراسان رضوی مشهد فکوری 50 و نسترن 100 وپلاک 600", 100, false);
        User user2 = new User("gol2", "mammad", "09309547432", "خراسان رضوی مشهد فکوری 50 لاله 15 وپلاک 621", 50, false);
        User user3 = new User("golabi", "soli", "09303447432", "خراسان رضوی تهران فکوری 42  بهار 15 وپلاک 5", 60, false);
        User user4 = new User("mosfasdf", "soli", "09303447432", "خراسان رضوی تهران فکوری 42  بهار 15 وپلاک 5", 60, false);
        User user5 = new User("goladsgdsgdsgbi", "soli", "09303447432", "خراسان رضوی تهران فکوری 42  بهار 15 وپلاک 5", 60, false);
        User user6 = new User("goladsgdsgdsgbi", "soli", "09303447432", "خراسان رضوی تهران فکوری 42  بهار 15 وپلاک 5", 60, false);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);

        gbc.gridwidth = 2;
        for (int i = 0; i < users.size(); i++) {
            gbc.gridy = i;
            lowerPanel.add(new UserInfoCardPanel(users.get(i)), gbc);
        }
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        JScrollPane scrollPanel = new JScrollPane(lowerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPanel, gridBagConstraints);
    }
}
