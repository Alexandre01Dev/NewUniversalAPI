package be.alexandre01.eloriamc.data.mysql;

public class DatabaseCredentials {

    private String host;
    private String user;
    private String pass;
    private String dbname;
    private int port;

    public DatabaseCredentials(String host, String user, String pass, String dbname, int port) {
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.dbname = dbname;
        this.port = port;
    }


    public String ToURL() {
        final StringBuilder sb = new StringBuilder();

        sb.append("jdbc:mysql://")
                .append(host)
                .append(":")
                .append(port)
                .append("/")
                .append(dbname)
                .append("?user=")
                .append(user)
                .append("&password=")
                .append(pass)
                .append("&useSSL=false");

        return sb.toString();
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
}
