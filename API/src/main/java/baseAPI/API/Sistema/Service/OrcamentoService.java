package baseAPI.API.Sistema.Service;

import baseAPI.API.Sistema.DTO.FornecedorDTO;
import baseAPI.API.Sistema.DTO.OrcamentoDTO;
import baseAPI.API.Sistema.Enum.Aparelho;
import baseAPI.API.Sistema.Enum.SelecionarAcaoBackup;
import baseAPI.API.Sistema.Model.*;
import baseAPI.API.Sistema.Repository.BackupRepository;
import baseAPI.API.Sistema.Repository.ClienteRepository;
import baseAPI.API.Sistema.Repository.ColaboradorRepository;
import baseAPI.API.Sistema.Repository.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
public class OrcamentoService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ColaboradorRepository colaboradorRepository;

    @Autowired
    OrcamentoRepository orcamentoRepository;

    @Autowired
    BackupRepository backupRepository;


    public ResponseEntity<List<Orcamento>> listarOrcamentos() throws Exception
    {
        try
        {
            return new ResponseEntity<>(orcamentoRepository.findAll(), OK);
        }
        catch (Exception e)
        {
            throw new Exception("erro ao listar");
        }
    }

    public ResponseEntity<Orcamento> BuscarOrcamentoPorId(Long id) throws Exception
    {
        try
        {
            if(orcamentoRepository.existsById(id))
            {
               Orcamento orcamento = orcamentoRepository.findById(id).get();
                return new ResponseEntity<>(orcamento, OK);
            }

        }
        catch (Exception e)
        {
            throw new Exception("erro ao listar");
        }
        return null;
    }

    public ResponseEntity<OrcamentoDTO> NovoOrcamento(OrcamentoDTO dto)
    {
        try{
            if(dto != null)
            {
                if(clienteRepository.existsBytelefone(dto.telefoneCLiente()))
                {
                    Cliente cliente = clienteRepository.findBytelefone(dto.telefoneCLiente());
                    if(colaboradorRepository.existsById(dto.idColaborador()))
                    {
                        Colaborador colaborador = colaboradorRepository.findById(dto.idColaborador()).get();
                        Orcamento orcamento = new Orcamento(dto);
                        orcamento.setDataEntrada(LocalDateTime.now());
                        orcamento.setCliente(cliente);
                        orcamento.setColaborador(colaborador);
                        orcamentoRepository.save(orcamento);
                        Backup backup = new Backup();
                        backup.setAcaoBackup(SelecionarAcaoBackup.NOVO_ORCAMENTO);
                        backup.setOrcamento(orcamento);
                        backup.setDataEvento(LocalDateTime.now());
                        backupRepository.save(backup);
                    }
                }

                return new ResponseEntity<>(CREATED);
            }
            else
            {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public ResponseEntity<OrcamentoDTO> EditarOrcamento(Long id, Aparelho aparelho, String relatoCliente)
    {
        try{
            if(orcamentoRepository.existsById(id))
            {
                Orcamento orcamento = orcamentoRepository.findById(id).get();
                orcamento.setAparelho(aparelho);
                orcamento.setRelatoCliente(relatoCliente);
                orcamentoRepository.save(orcamento);
                Backup backup = new Backup();
                backup.setOrcamento(orcamento);
                backup.setDataEvento(LocalDateTime.now());
                backup.setAcaoBackup(SelecionarAcaoBackup.EDITAR_ORCAMENTO);
                return new ResponseEntity<>(OK);
            }
            else
            {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public ResponseEntity<FornecedorDTO> DeletarOrcamento(Long id) throws Exception
    {
        try
        {
            if(orcamentoRepository.existsById(id))
            {
                orcamentoRepository.deleteById(id);
            }
            return new ResponseEntity<>(OK);
        }
        catch (Exception e)
        {
            throw new Exception("erro ao deletar");
        }
    }

}
