package ipc2.bank.database;

import ipc2.bank.encriptador.Encriptador;
import ipc2.bank.exceptions.NoAtributeFoundException;
import ipc2.bank.exceptions.NoConnectionFoundException;
import ipc2.bank.models.Turno;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import ipc2.bank.models.User;
import ipc2.bank.util.Funcionalidad;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yenni
 */
public class UserDB {

    private Connection connection;
    private Encriptador encriptador;
    public static final int CLIENTE = 1, CAJERO = 2, GERENTE = 3;
    public static final String CREDENTIALS_BANCA_VIRTUAL = "101";
    public static final int ID_BANCA_VIRTUAL = 101;
    public UserDB(Connection connection) {
        this.connection = connection;
        this.encriptador = new Encriptador();

    }

    public UserDB(HttpSession session) throws NoConnectionFoundException {
        this.connection = (Connection) session.getAttribute("conexion");
        if (this.connection == null) {
            throw new NoConnectionFoundException();
        }
    }
    
    public List<User> getAll(int typeUser){
        String query = "SELECT * FROM usuario WHERE tipoUsuario = ?";
        try {
            PreparedStatement select = connection.prepareStatement(query);

            List<User> users = new ArrayList<>();
            select.setInt(1, typeUser);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                if(rs.getInt("codigo")!= ID_BANCA_VIRTUAL){
                    users.add(new User(rs));
                }
            }
            return users;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    /**
     * Autentica el usuario, para ingresar a la pagina web
     * @param request del servlet para obtener las credenciales y la contrasena
     * @return boolean que indica si el usuario existe o no
     */
    public boolean autenticar(HttpServletRequest request) {
        String credentials = request.getParameter("credentials");
        String password = request.getParameter("password");
        Optional<User> user = obtener(credentials, password);
        HttpSession session = request.getSession();
        if (user.isPresent()) {
            session.setAttribute("user", user.get());
        }
        return user.isPresent();
    }

    /**
     * Obtiene un usuario opcional a partir de credenciales y una contrasena
     * @param credentials del usuario, el numero de dpi
     * @param password
     * @return optional<user> pues si no encuentra las credenciales es null
     */
    public Optional<User> obtener(String credentials, String password) {
        User user = null;
        if(!credentials.equals(CREDENTIALS_BANCA_VIRTUAL)){
            String query = "SELECT * FROM usuario WHERE noIdentificacion = ? AND password = ?";
            String encryptedPassword = encriptador.encriptar(password);
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, credentials);
                ps.setString(2, encryptedPassword);
                try (ResultSet resultSet = ps.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User(resultSet);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error al consultar: " + e);
            }
        }
        return Optional.ofNullable(user);
    }
    
    public Optional<User> obtener(int id) {
        User user = null;
        String query = "SELECT * FROM usuario WHERE codigo = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> obtener(String credentials){
        String query = "SELECT * FROM usuario WHERE noIdentificacion = ?";
        User user = null;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, credentials);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
        }
        return Optional.ofNullable(user);
    }
    /**
     * Consulta la base de datos para verificar si ya existen ciertas
     * credenciales
     *
     * @param credentials para verificar coincidencias
     * @return true si existe, false de lo contrario
     */
    public boolean exist(String credentials) {
        String query = "SELECT * FROM usuario WHERE noIdentificacion = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, credentials);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
            return false;
        }
    }
    public boolean exist(int idUser) {
        String query = "SELECT * FROM usuario WHERE codigo = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idUser);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
            return false;
        }
    }
    public boolean isType(int idUser, int typeUser){
        boolean condicion = false;
        Optional<User> userOp = this.obtener(idUser);
        if(userOp.isPresent()){
            return userOp.get().getTipoUsuario() == typeUser;
        }
        return condicion;
    }

    /**
     * Obtiene el link de redireccion al dashboard de los perfiles ademas
     * actualiza sus funciones a partir del usuario asignado a la sesion si no
     * hay ningun usuario seteado no inicializa nada y muestra el dashboard
     * vacio
     *
     * @param request para obtener la sesion y el usuario asociado a ella
     * @return String url del dashboard
     */
    public String getRedirectDashboard(HttpServletRequest request) {
        //mismo jsp que obtiene que a la larga va a redirigir los diferentes modulos
        String url = "/dashboard.jsp";
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        currentUser.setFunctions(getFuncionalidades(currentUser.getTipoUsuario()));
        session.setAttribute("conexion", this.connection);
        return url;
    }

    /**
     * Evalua el tipo de usuario y retorna las funcionalidades correspondientes
     *
     * @throws AssertionError cuando se ingresa un tipo de usuario inexistente
     * @param tipoUsuario el codigo del tipo de usuario
     * @return Funcionalidad[] lista de funcionalidades de cada tipo de usuario
     */
    private Funcionalidad[] getFuncionalidades(int tipoUsuario) throws AssertionError {
        switch (tipoUsuario) {
            case 1: //cliente
                Funcionalidad[] funC = {
                    new Funcionalidad("Verificar estado de cuenta", "AcountStatus"),
                    new Funcionalidad("Transferir dinero entre cuentas asociadas", "Transferir"),
                    new Funcionalidad("Transferencia a terceros", "TransferenciaTerceros"),
                    new Funcionalidad("Crear solicitud de asociacion", ""),
                    new Funcionalidad("Ver solicitudes de asociacion", ""),
                    new Funcionalidad("Visualizar reportes", "" )
                };
                return funC;
            case 2: //cajero
                Funcionalidad[] funCa = {
                    new Funcionalidad("Depositar", "Depositar"),
                    new Funcionalidad("Retirar", "Retirar"),
                    new Funcionalidad("Visualizar reportes", ""),};
                return funCa;
            case 3: //gerente
                Funcionalidad[] funG = {
                    new Funcionalidad("Crear cuenta de cliente", "CreateUser"),
                    new Funcionalidad("Crear cuenta bancaria", "CreateAcount"),
                    new Funcionalidad("Ver y actualizar perfil", "UpdateInfo"),
                    new Funcionalidad("Actualizar datos de cajeros", "DisplayCajeros"),
                    new Funcionalidad("Actualizar datos de clientes", "DisplayClientes"),
                    new Funcionalidad("Actualizar Parametros del sistema", "SystemParam"),
                    new Funcionalidad("Visualizar reportes", "")
                };
                return funG;
            default:
                throw new AssertionError();
        }
    }

    /**
     * Valida los horarios de un usuario
     *
     * @param session de la sesion va obtener el atributo user, que servira para
     * evaluar su tipo
     * @return true si el horario permite acceder, false de lo contrario
     * @throws ipc2.bank.exceptions.NoAtributeFoundException si el empleasdo no
     * cuenta con un horario, es un error de la base de datos
     */
    public boolean validateScedule(HttpSession session)
            throws NoAtributeFoundException, NullPointerException {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser.getTipoUsuario() == 2 || currentUser.getTipoUsuario() == 3) {
            TurnoDB turnoDB = new TurnoDB(connection);
            Optional<Turno> turnoOp = turnoDB.getTurno(currentUser.getId());
            if (turnoOp.isPresent()) {
                return turnoDB.validarTurno(turnoOp.get());
            } else {
                throw new NoAtributeFoundException();
            }
        } else {
            return true;
        }
    }

    /**
     * @param user el usuario que se insertara en la base de datos
     * @return boolean que indica si se inserto correctamente o no
     */
    public boolean insertIntoDB(User user) {
        if (user.isValid() && !exist(user.getNoIdentificacion())) {
            String query = "INSERT INTO usuario(nombre, direccion, noIdentificacion, sexo, password, tipoUsuario) "
                    + "VALUES (?, ?, ?, ?, ?, ?);";
            try {
                PreparedStatement insert = connection.prepareStatement(query);
                insert.setString(1, user.getName());
                insert.setString(2, user.getAddress());
                insert.setString(3, user.getNoIdentificacion());
                insert.setString(4, user.getSexo());
                String encryptedPassword = new Encriptador().encriptar(user.getPassword());
                insert.setString(5, encryptedPassword);
                insert.setInt(6, user.getTipoUsuario());
                insert.executeUpdate();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean updateIntoDB(User updateUser) { 
        try {
            String query = "UPDATE usuario SET nombre = ?, "
                    + "direccion = ?,"
                    + "noIdentificacion = ?, "
                    + "sexo = ?, "
                    + "password = ? "
                    + "WHERE codigo = ?;";
            PreparedStatement update = connection.prepareStatement(query);
            update.setString(1, updateUser.getName());
            update.setString(2, updateUser.getAddress());
            update.setString(3, updateUser.getNoIdentificacion());
            update.setString(4, updateUser.getSexo());
            String encryptedPassword = new Encriptador().encriptar(updateUser.getPassword());
            update.setString(5, encryptedPassword);
            update.setInt(6, updateUser.getId());
            update.executeUpdate();
            System.out.println("Updated de un user exitoso");
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

}
