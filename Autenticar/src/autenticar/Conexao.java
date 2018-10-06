package autenticar;

/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;*/
import java.util.Properties;
import javax.swing.JOptionPane;
import java.sql.*;

public class Conexao {
	
	// string URL padrão
	// endereço: localhost
	// base de dados: mtp
	private String url = "jdbc:postgresql://localhost/credenciais";
	
	// usuário do postgres
	private String usuario = "postgres";
	
	// senha do postgres
	private String senha = "postgres";
	
	// variável que guarda a conexão
	private Connection conn;
	
	/**
	 * Método construtor.
	 * 
	 * Toda vez que instanciar essa classe, a conexão é automaticamente feita
	 */
	public Conexao() {
		conectar();
	}
	
	/**
	 * Método para conexão com o banco de dados.
	 * 
	 * Carrega o driver e cria a conexão com o BD.
	 */
	public void conectar() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Properties props = new Properties();
		props.setProperty("user", this.usuario);
		props.setProperty("password", this.senha);
		
		try {
			this.conn = DriverManager.getConnection(this.url, props);
		} catch (SQLException e) {
			e.getMessage();
		}

	}
	
	/**
	 * Método que retorna a conexão feita com o BD
	 * 
	 * @return um objeto Connection que é a conexão feita com o BD
	 */
	public Connection getConnection() {
		return this.conn;
	}
	
	/**
	 * Método que cria a tabela pessoa para este exemplo.
	 * 
	 * Normalmente, a criação de tabelas NÃO é feita pela aplicação.
	 */
	/*public void criarTabela() {
		try {
			PreparedStatement st = this.conn.prepareStatement("CREATE TABLE usuario (id serial primary key, nome text)");
			st.execute();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * Método que insere uma pessoa no banco de dados
	 * 
	 * Por enquanto, a pessoa está fixa!
	 */
	public void inserir(String usuario, String nome, String email, String ce, String senha, String confirmaSenha) {
            try {
                PreparedStatement st = this.conn.prepareStatement("INSERT INTO usuario (usuario,nome,email,ce,senha,confirma_senha) VALUES (?,?,?,?,?,?)");
                st.setString(1, usuario);
                st.setString(2, nome);
                st.setString(3, email);
                st.setString(4, ce);
                st.setString(5, senha);
                st.setString(6, confirmaSenha);
                st.executeUpdate();
                st.close();
            } 
            catch (SQLException e) {
		JOptionPane.showMessageDialog(null , e , "Erro" , JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
	}
	
        public boolean Consultar (String usuario, String senha) {
            boolean T = false;
            try {
                
               PreparedStatement stmt = conn.prepareStatement("SELECT *FROM usuario WHERE usuario = ? AND senha = ?;");
               stmt.setString(1,usuario);
               stmt.setString(2,senha);
               ResultSet rs = stmt.executeQuery();
               while (rs.next()) {
                    String usuariodois = rs.getString("usuario");
                    String senhadois = rs.getString("senha");
                    //System.out.println(usuariodois);
                    //System.out.println(senhadois);
                    if ((usuario.equals(usuariodois)) && (senha.equals(senhadois))) {
                        T = true;
                    }
                    else {
                        T = false;
                    }
                }
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(null , e , "Erro" , JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            return T;
        }
        
        public boolean ConsultarExistente (String usuario) {
            boolean T = false;
            
            try {
                PreparedStatement stmt = conn.prepareStatement("SELECT *FROM usuario WHERE usuario = ?;");
                stmt.setString(1, usuario);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String usuariodois = rs.getString("usuario");
                    System.out.println(usuariodois);
                    if ((usuario.equals(usuariodois))) {
                        T = true;
                    }
                    else {
                        T = false;
                    }
                }
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(null , e , "Erro" , JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            return T;
        }
	/**
	 * Método que atualiza todos os nomes do banco de dados
	 * 
	 * E se for necessário alterar para uma pessoa só? O que muda?
	 */
	/*public void atualizar() {
		try {
			PreparedStatement st = this.conn.prepareStatement("UPDATE usuario SET nome = ?");
			st.setString(1, "Thiago 2");
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * Método que exclui uma determinada pessoa do banco de dados
	 * 
	 * Está sempre excluindo a mesma pessoa! A que tem ID = 1!
	 */
	/*public void excluir() {
		try {
			PreparedStatement st = this.conn.prepareStatement("DELETE FROM usuario WHERE id = ?");
			st.setInt(1, 1);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
}
