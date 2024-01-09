import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class VeritabaniUygulamasi extends JFrame {

    private JTextField ipAdresiField, veritabaniAdiField, kullaniciAdiField, sifreField;
    private JTextArea sonucTextArea;
    private JButton baglanButton, tablolariListeleButton;

    public VeritabaniUygulamasi() {
        setTitle("Veritabanı Uygulaması");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 5, 5));

        ipAdresiField = new JTextField();
        veritabaniAdiField = new JTextField();
        kullaniciAdiField = new JTextField();
        sifreField = new JPasswordField();
        sonucTextArea = new JTextArea();
        baglanButton = new JButton("Bağlan");
        tablolariListeleButton = new JButton("Tabloları Listele");

        panel.add(new JLabel("Veritabanı IP Adresi:"));
        panel.add(ipAdresiField);
        panel.add(new JLabel("Veritabanı İsmi:"));
        panel.add(veritabaniAdiField);
        panel.add(new JLabel("Kullanıcı Adı:"));
        panel.add(kullaniciAdiField);
        panel.add(new JLabel("Şifre:"));
        panel.add(sifreField);
        panel.add(baglanButton);
        panel.add(tablolariListeleButton);

        baglanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                baglantiyiKontrolEt();
            }
        });

        tablolariListeleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tablolariListele();
            }
        });

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(sonucTextArea), BorderLayout.CENTER);
    }

    private void baglantiyiKontrolEt() {
        try {
            // JDBC sürücüsünü yükle
            Class.forName("com.mysql.cj.jdbc.Driver");

            String ipAdresi = ipAdresiField.getText();
            String veritabaniAdi = veritabaniAdiField.getText();
            String kullaniciAdi = kullaniciAdiField.getText();
            String sifre = sifreField.getText();

            if (ipAdresi.isEmpty() || veritabaniAdi.isEmpty() || kullaniciAdi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String url = "jdbc:mysql://" + ipAdresi + ":3306/" + veritabaniAdi + "?useSSL=false";
            Connection connection = DriverManager.getConnection(url, kullaniciAdi, sifre);
            sonucTextArea.setText("Bağlantı Başarılı!");
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            sonucTextArea.setText("Bağlantı Hatası: " + ex.getMessage());
        }
    }

    private void tablolariListele() {
        try {
            // JDBC sürücüsünü yükle
            Class.forName("com.mysql.cj.jdbc.Driver");

            String ipAdresi = ipAdresiField.getText();
            String veritabaniAdi = veritabaniAdiField.getText();
            String kullaniciAdi = kullaniciAdiField.getText();
            String sifre = sifreField.getText();

            if (ipAdresi.isEmpty() || veritabaniAdi.isEmpty() || kullaniciAdi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String url = "jdbc:mysql://" + ipAdresi + ":3306/" + veritabaniAdi + "?useSSL=false";
            Connection connection = DriverManager.getConnection(url, kullaniciAdi, sifre);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW TABLES");

            StringBuilder tablolar = new StringBuilder("Mevcut Tablolar:\n");
            while (resultSet.next()) {
                tablolar.append(resultSet.getString(1)).append("\n");
            }

            sonucTextArea.setText(tablolar.toString());

            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            sonucTextArea.setText("Bağlantı Hatası: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VeritabaniUygulamasi().setVisible(true);
            }
        });
    }
}
