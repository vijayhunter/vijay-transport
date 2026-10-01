import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VJTransport extends JFrame {
    private final Color navy = new Color(25, 35, 85);
    private final Color purple = new Color(112, 65, 190);
    private final Color teal = new Color(0, 160, 150);
    private final Color orange = new Color(245, 145, 55);
    private final Color background = new Color(245, 247, 252);

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel content = new JPanel(cardLayout);
    private final List<String[]> passengers = new ArrayList<>();
    private final List<String[]> buses = new ArrayList<>();
    private final List<String[]> bookings = new ArrayList<>();
    private DefaultTableModel passengerModel, busModel, bookingModel;
    private JLabel passengerCount, busCount, bookingCount, revenueCount;

    public VJTransport() {
        setTitle("VJ Transport Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1120, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(background);

        JPanel sidebar = new JPanel();
        sidebar.setBackground(navy);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("<html><center><span style='font-size:25px'>🚌 VJ</span><br>"
                + "<span style='font-size:13px'>TRANSPORT</span></center></html>");
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setBorder(BorderFactory.createEmptyBorder(28, 5, 28, 5));
        sidebar.add(logo);

        addNavButton(sidebar, "🏠   Dashboard", "Dashboard");
        addNavButton(sidebar, "👥   Passengers", "Passengers");
        addNavButton(sidebar, "🚌   Bus Details", "Buses");
        addNavButton(sidebar, "🎫   Ticket Booking", "Booking");
        addNavButton(sidebar, "📋   Booking History", "History");
        sidebar.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("<html><center>VJ Transport<br>Management System</center></html>");
        footer.setForeground(new Color(190, 200, 230));
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);
        footer.setBorder(BorderFactory.createEmptyBorder(15, 5, 22, 5));
        sidebar.add(footer);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(background);
        JPanel topbar = new JPanel(new BorderLayout());
        topbar.setBackground(Color.WHITE);
        topbar.setBorder(BorderFactory.createEmptyBorder(17, 25, 17, 25));
        JLabel heading = new JLabel("VJ TRANSPORT  |  Management Portal");
        heading.setFont(new Font("SansSerif", Font.BOLD, 21));
        heading.setForeground(navy);
        JLabel status = new JLabel("● SYSTEM READY");
        status.setForeground(new Color(0, 145, 95));
        status.setFont(new Font("SansSerif", Font.BOLD, 12));
        topbar.add(heading, BorderLayout.WEST);
        topbar.add(status, BorderLayout.EAST);

        content.setBackground(background);
        content.add(createDashboard(), "Dashboard");
        content.add(createPassengers(), "Passengers");
        content.add(createBuses(), "Buses");
        content.add(createBooking(), "Booking");
        content.add(createHistory(), "History");

        main.add(topbar, BorderLayout.NORTH);
        main.add(content, BorderLayout.CENTER);
        add(sidebar, BorderLayout.WEST);
        add(main, BorderLayout.CENTER);
        refreshDashboard();
    }

    private void addNavButton(JPanel sidebar, String text, String page) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(220, 48));
        button.setPreferredSize(new Dimension(220, 48));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(0, 22, 0, 8));
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(navy);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> {
            cardLayout.show(content, page);
            if ("Dashboard".equals(page)) refreshDashboard();
        });
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { button.setBackground(purple); }
            public void mouseExited(java.awt.event.MouseEvent e) { button.setBackground(navy); }
        });
        sidebar.add(button);
    }

    private JPanel pagePanel(String title, String subtitle) {
        JPanel panel = new JPanel(new BorderLayout(0, 18));
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        JPanel intro = new JPanel();
        intro.setLayout(new BoxLayout(intro, BoxLayout.Y_AXIS));
        intro.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(navy);
        JLabel sub = new JLabel(subtitle);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sub.setForeground(new Color(105, 115, 135));
        intro.add(titleLabel);
        intro.add(Box.createVerticalStrut(6));
        intro.add(sub);
        panel.add(intro, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createDashboard() {
        JPanel panel = pagePanel("Dashboard", "Welcome to your VJ Transport control center.");
        JPanel body = new JPanel(new BorderLayout(0, 20));
        body.setOpaque(false);

        JPanel cards = new JPanel(new GridLayout(2, 2, 16, 16));
        cards.setOpaque(false);
        passengerCount = new JLabel("0");
        busCount = new JLabel("0");
        bookingCount = new JLabel("0");
        revenueCount = new JLabel("₹0");
        cards.add(statCard("TOTAL PASSENGERS", passengerCount, purple, "Registered passengers"));
        cards.add(statCard("BUSES REGISTERED", busCount, teal, "Fleet records"));
        cards.add(statCard("TICKETS BOOKED", bookingCount, orange, "All bookings"));
        cards.add(statCard("TOTAL FARE", revenueCount, new Color(220, 75, 110), "Value of recorded bookings"));

        JPanel welcome = new JPanel(new BorderLayout());
        welcome.setBackground(Color.WHITE);
        welcome.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 233, 242)),
                BorderFactory.createEmptyBorder(22, 22, 22, 22)));
        JLabel welcomeTitle = new JLabel("Your journey, managed simply.");
        welcomeTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        welcomeTitle.setForeground(navy);
        JLabel welcomeText = new JLabel("<html>Use the menu to register passengers, add bus information, "
                + "book tickets and review booking history.</html>");
        welcomeText.setForeground(new Color(95, 105, 125));
        welcomeText.setFont(new Font("SansSerif", Font.PLAIN, 14));
        welcome.add(welcomeTitle, BorderLayout.NORTH);
        welcome.add(welcomeText, BorderLayout.CENTER);
        body.add(cards, BorderLayout.CENTER);
        body.add(welcome, BorderLayout.SOUTH);
        panel.add(body, BorderLayout.CENTER);
        return panel;
    }

    private JPanel statCard(String title, JLabel value, Color color, String caption) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, color),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));
        JLabel heading = new JLabel(title);
        heading.setForeground(new Color(105, 115, 135));
        heading.setFont(new Font("SansSerif", Font.BOLD, 12));
        value.setForeground(color);
        value.setFont(new Font("SansSerif", Font.BOLD, 30));
        JLabel small = new JLabel(caption);
        small.setForeground(new Color(140, 145, 160));
        small.setFont(new Font("SansSerif", Font.PLAIN, 12));
        JPanel middle = new JPanel(new BorderLayout());
        middle.setOpaque(false);
        middle.add(value, BorderLayout.CENTER);
        middle.add(small, BorderLayout.SOUTH);
        card.add(heading, BorderLayout.NORTH);
        card.add(middle, BorderLayout.CENTER);
        return card;
    }

    private JPanel createPassengers() {
        JPanel panel = pagePanel("Passenger Registration", "Add and view passenger details.");
        JPanel form = new JPanel(new GridLayout(2, 4, 12, 10));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 233, 242)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        JTextField name = field(), phone = field(), age = field();
        JComboBox<String> gender = new JComboBox<>(new String[]{"Select gender", "Female", "Male", "Other"});
        styleCombo(gender);
        form.add(labeled("Full Name", name));
        form.add(labeled("Phone Number", phone));
        form.add(labeled("Age", age));
        form.add(labeled("Gender", gender));

        JButton add = primaryButton("＋ Add Passenger", purple);
        add.addActionListener(e -> {
            if (name.getText().trim().isEmpty() || phone.getText().trim().isEmpty()
                    || age.getText().trim().isEmpty() || gender.getSelectedIndex() == 0) {
                message("Please fill in all passenger fields.");
                return;
            }
            if (!phone.getText().trim().matches("[0-9+() -]{7,18}")) {
                message("Please enter a valid phone number.");
                return;
            }
            try {
                int a = Integer.parseInt(age.getText().trim());
                if (a < 1 || a > 120) { message("Enter an age between 1 and 120."); return; }
            } catch (NumberFormatException ex) { message("Age must be a number."); return; }
            String[] row = {name.getText().trim(), phone.getText().trim(), age.getText().trim(),
                    gender.getSelectedItem().toString()};
            passengers.add(row);
            passengerModel.addRow(row);
            name.setText(""); phone.setText(""); age.setText(""); gender.setSelectedIndex(0);
            refreshDashboard();
            message("Passenger registered successfully.");
        });
        JPanel top = new JPanel(new BorderLayout(0, 12));
        top.setOpaque(false);
        top.add(form, BorderLayout.CENTER);
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(add);
        top.add(buttonRow, BorderLayout.SOUTH);

        passengerModel = model("Name", "Phone", "Age", "Gender");
        JTable table = createTable(passengerModel);
        panel.add(top, BorderLayout.CENTER);
        panel.add(tablePanel(table), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createBuses() {
        JPanel panel = pagePanel("Bus Details", "Maintain your bus fleet and route information.");
        JPanel form = new JPanel(new GridLayout(2, 4, 12, 10));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 233, 242)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        JTextField busNo = field(), route = field(), capacity = field(), fare = field();
        form.add(labeled("Bus Number", busNo));
        form.add(labeled("Route (From - To)", route));
        form.add(labeled("Seat Capacity", capacity));
        form.add(labeled("Fare (₹)", fare));

        JButton add = primaryButton("＋ Add Bus", teal);
        add.addActionListener(e -> {
            if (busNo.getText().trim().isEmpty() || route.getText().trim().isEmpty()
                    || capacity.getText().trim().isEmpty() || fare.getText().trim().isEmpty()) {
                message("Please fill in all bus fields."); return;
            }
            try {
                int seats = Integer.parseInt(capacity.getText().trim());
                double price = Double.parseDouble(fare.getText().trim());
                if (seats < 1 || price < 0) { message("Capacity must be positive and fare cannot be negative."); return; }
                for (String[] b : buses) if (b[0].equalsIgnoreCase(busNo.getText().trim())) {
                    message("That bus number already exists."); return;
                }
                String[] row = {busNo.getText().trim(), route.getText().trim(), String.valueOf(seats),
                        String.format("%.2f", price)};
                buses.add(row); busModel.addRow(row);
                busNo.setText(""); route.setText(""); capacity.setText(""); fare.setText("");
                refreshDashboard(); message("Bus added successfully.");
            } catch (NumberFormatException ex) { message("Capacity and fare must be valid numbers."); }
        });
        JPanel top = new JPanel(new BorderLayout(0, 12));
        top.setOpaque(false); top.add(form, BorderLayout.CENTER);
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonRow.setOpaque(false); buttonRow.add(add); top.add(buttonRow, BorderLayout.SOUTH);
        busModel = model("Bus Number", "Route", "Seat Capacity", "Fare (₹)");
        JTable table = createTable(busModel);
        panel.add(top, BorderLayout.CENTER);
        panel.add(tablePanel(table), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createBooking() {
        JPanel panel = pagePanel("Ticket Booking", "Choose a passenger and a registered bus to calculate the fare.");
        JPanel form = new JPanel(new GridLayout(5, 2, 14, 14));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 233, 242)),
                BorderFactory.createEmptyBorder(22, 22, 22, 22)));
        JComboBox<String> passenger = new JComboBox<>();
        JComboBox<String> bus = new JComboBox<>();
        styleCombo(passenger); styleCombo(bus);
        JTextField seats = field();
        JLabel total = new JLabel("₹0.00");
        total.setFont(new Font("SansSerif", Font.BOLD, 22)); total.setForeground(teal);
        JButton calculate = primaryButton("Calculate Fare", orange);
        JButton book = primaryButton("Confirm Booking", purple);
        form.add(fieldLabel("Passenger")); form.add(passenger);
        form.add(fieldLabel("Bus / Route")); form.add(bus);
        form.add(fieldLabel("Number of Seats")); form.add(seats);
        form.add(fieldLabel("Total Fare")); form.add(total);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.setOpaque(false); actions.add(calculate); actions.add(book);
        form.add(new JLabel("")); form.add(actions);

        final double[] currentFare = {0};
        Runnable updateFare = () -> {
            try {
                int count = Integer.parseInt(seats.getText().trim());
                int index = bus.getSelectedIndex();
                if (index < 0 || count < 1) { currentFare[0] = 0; total.setText("₹0.00"); return; }
                currentFare[0] = Double.parseDouble(buses.get(index)[3]) * count;
                total.setText(String.format("₹%.2f", currentFare[0]));
            } catch (Exception ex) { currentFare[0] = 0; total.setText("Enter valid seats"); }
        };
        calculate.addActionListener(e -> updateFare.run());
        bus.addActionListener(e -> updateFare.run());
        book.addActionListener(e -> {
            updateFare.run();
            int pi = passenger.getSelectedIndex(), bi = bus.getSelectedIndex();
            if (pi < 0 || bi < 0) { message("Register a passenger and add a bus first."); return; }
            int count;
            try { count = Integer.parseInt(seats.getText().trim()); }
            catch (NumberFormatException ex) { message("Enter a valid number of seats."); return; }
            if (count < 1) { message("Number of seats must be at least 1."); return; }
            int capacity;
            try { capacity = Integer.parseInt(buses.get(bi)[2]); }
            catch (Exception ex) { capacity = 0; }
            if (count > capacity) { message("Seat count cannot exceed the bus capacity."); return; }
            String[] p = passengers.get(pi), b = buses.get(bi);
            String[] row = {p[0], b[0], b[1], String.valueOf(count), String.format("%.2f", currentFare[0])};
            bookings.add(row); bookingModel.addRow(row);
            refreshDashboard(); message("Ticket booking recorded successfully.");
        });

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        JLabel note = new JLabel("<html><b>Note:</b> This demo records bookings in memory. Data resets when the app closes.</html>");
        note.setForeground(new Color(110, 115, 135));
        note.setBorder(BorderFactory.createEmptyBorder(15, 3, 0, 3));
        center.add(form, BorderLayout.NORTH); center.add(note, BorderLayout.CENTER);
        panel.add(center, BorderLayout.CENTER);

        // Update combo-box choices whenever this page is opened.
        panel.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & java.awt.event.HierarchyEvent.SHOWING_CHANGED) != 0 && panel.isShowing()) {
                passenger.removeAllItems();
                for (String[] p : passengers) passenger.addItem(p[0] + " — " + p[1]);
                bus.removeAllItems();
                for (String[] b : buses) bus.addItem(b[0] + " — " + b[1]);
                updateFare.run();
            }
        });
        return panel;
    }

    private JPanel createHistory() {
        JPanel panel = pagePanel("Booking History", "Review tickets created during this session.");
        bookingModel = model("Passenger", "Bus Number", "Route", "Seats", "Total Fare (₹)");
        JTable table = createTable(bookingModel);
        JButton refresh = primaryButton("↻ Refresh", purple);
        refresh.addActionListener(e -> {
            bookingModel.setRowCount(0);
            for (String[] row : bookings) bookingModel.addRow(row);
        });
        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        top.setOpaque(false); top.add(refresh);
        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.setOpaque(false); center.add(top, BorderLayout.NORTH); center.add(tablePanel(table), BorderLayout.CENTER);
        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    private void refreshDashboard() {
        if (passengerCount != null) passengerCount.setText(String.valueOf(passengers.size()));
        if (busCount != null) busCount.setText(String.valueOf(buses.size()));
        if (bookingCount != null) bookingCount.setText(String.valueOf(bookings.size()));
        if (revenueCount != null) {
            double total = 0;
            for (String[] b : bookings) try { total += Double.parseDouble(b[4]); } catch (Exception ignored) {}
            revenueCount.setText(String.format("₹%.0f", total));
        }
    }

    private JTextField field() {
        JTextField f = new JTextField();
        f.setFont(new Font("SansSerif", Font.PLAIN, 14));
        f.setPreferredSize(new Dimension(160, 34));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 220, 232)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        return f;
    }

    private JPanel labeled(String text, JComponent input) {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setOpaque(false);
        JLabel label = fieldLabel(text);
        p.add(label, BorderLayout.NORTH);
        p.add(input, BorderLayout.CENTER);
        return p;
    }

    private JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(navy);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        return l;
    }

    private void styleCombo(JComboBox<?> combo) {
        combo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(160, 34));
    }

    private JButton primaryButton(String text, Color color) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setForeground(Color.WHITE);
        b.setBackground(color);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private DefaultTableModel model(String... columns) {
        return new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
    }

    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(225, 218, 250));
        table.setGridColor(new Color(235, 237, 244));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setBackground(navy);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFillsViewportHeight(true);
        return table;
    }

    private JScrollPane tablePanel(JTable table) {
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(700, 190));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(225, 229, 239)));
        return scroll;
    }

    private void message(String text) {
        JOptionPane.showMessageDialog(this, text, "VJ Transport", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new VJTransport().setVisible(true);
        });
    }
}
