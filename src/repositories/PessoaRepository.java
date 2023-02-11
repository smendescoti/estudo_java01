package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entities.Pessoa;
import factories.ConnectionFactory;
import interfaces.IRepository;

public class PessoaRepository implements IRepository<Pessoa> {

	@Override
	public void save(Pessoa entity) throws Exception {

		//abrir conexão com o banco de dados
		Connection connection = ConnectionFactory.getConnection();

		//cadastrando pessoa na tabela do banco de dados
		PreparedStatement statement = connection.prepareStatement("insert into pessoa(nome, email) values(?,?)");
		statement.setString(1, entity.getNome());
		statement.setString(2, entity.getEmail());
		statement.execute();
		
		connection.close();
	}

	@Override
	public void update(Pessoa entity) throws Exception {

		//abrir conexão com o banco de dados
		Connection connection = ConnectionFactory.getConnection();
		
		//atualizando pessoa na tabela do banco de dados
		PreparedStatement statement = connection.prepareStatement("update pessoa set nome=?, email=? where idpessoa=?");
		statement.setString(1, entity.getNome());
		statement.setString(2, entity.getEmail());
		statement.setInt(3, entity.getIdPessoa());
		statement.execute();
		
		connection.close();
	}

	@Override
	public void delete(Pessoa entity) throws Exception {

		//abrir conexão com o banco de dados
		Connection connection = ConnectionFactory.getConnection();
		
		//excluindo pessoa na tabela do banco de dados
		PreparedStatement statement = connection.prepareStatement("delete from pessoa where idpessoa=?");
		statement.setInt(1, entity.getIdPessoa());
		statement.execute();
		
		connection.close();
	}

	@Override
	public List<Pessoa> findAll() throws Exception {

		//abrir conexão com o banco de dados
		Connection connection = ConnectionFactory.getConnection();
		
		//consultando todos os registros contidos na tabela pessoa
		PreparedStatement statement = connection.prepareStatement("select * from pessoa");
		ResultSet resultSet = statement.executeQuery();
		
		//criando uma lista de objetos para Pessoa
		List<Pessoa> lista = new ArrayList<Pessoa>();
		
		//percorrendo cada registro obtido do banco de dados
		while(resultSet.next()) {
			
			Pessoa pessoa = new Pessoa();
			
			pessoa.setIdPessoa(resultSet.getInt("idpessoa"));
			pessoa.setNome(resultSet.getString("nome"));
			pessoa.setEmail(resultSet.getString("email"));
			
			lista.add(pessoa);
		}
		
		connection.close();
		return lista;
	}
	
	public List<Pessoa> findByNome(String nome) throws Exception {
		
		Connection connection = ConnectionFactory.getConnection();
		
		PreparedStatement statement = connection.prepareStatement("select * from pessoa where nome like ? order by nome");
		statement.setString(1, "%" + nome + "%");
		ResultSet resultSet = statement.executeQuery();
		
		List<Pessoa> lista = new ArrayList<Pessoa>();
		
		while(resultSet.next()) {
			
			Pessoa pessoa = new Pessoa();
			
			pessoa.setIdPessoa(resultSet.getInt("idpessoa"));
			pessoa.setNome(resultSet.getString("nome"));
			pessoa.setEmail(resultSet.getString("email"));
			
			lista.add(pessoa);
		}
		
		connection.close();
		return lista;
	}

	@Override
	public Pessoa findById(Integer id) throws Exception {

		Connection connection = ConnectionFactory.getConnection();
		
		PreparedStatement statement = connection.prepareStatement("select * from pessoa where idpessoa=?");
		statement.setInt(1, id);
		ResultSet resultSet = statement.executeQuery();
		
		Pessoa pessoa = null; //vazio
		
		if(resultSet.next()) {
			
			pessoa = new Pessoa();
			
			pessoa.setIdPessoa(resultSet.getInt("idpessoa"));
			pessoa.setNome(resultSet.getString("nome"));
			pessoa.setEmail(resultSet.getString("email"));
		}
		
		connection.close();
		return pessoa;
	}

}







