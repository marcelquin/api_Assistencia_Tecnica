package baseAPI.API.Sistema.Service;

import baseAPI.API.Sistema.DTO.FornecedorDTO;
import baseAPI.API.Sistema.DTO.ItemReparoDTO;
import baseAPI.API.Sistema.DTO.OrdemServicoDTO;
import baseAPI.API.Sistema.Enum.SelecionarAcaoBackup;
import baseAPI.API.Sistema.Enum.SelecionarPagamento;
import baseAPI.API.Sistema.Enum.StatusOrdenServico;
import baseAPI.API.Sistema.Model.*;
import baseAPI.API.Sistema.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service

public class OrdemServicoService {

    @Autowired
    OrcamentoRepository orcamentoRepository;

    @Autowired
    OrdemServicoRepository ordemServicoRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ItemReparoRepository itemReparoRepository;

    @Autowired
    BackupRepository backupRepository;

    public ResponseEntity<List<OrdemServico>> listarOrdemServico() throws Exception
    {
        try
        {
            return new ResponseEntity<>(ordemServicoRepository.findAll(), OK);
        }
        catch (Exception e)
        {
            throw new Exception("erro ao listar");
        }
    }

    public ResponseEntity<OrdemServico> BuscarOrdemServicoPorId(Long id) throws Exception
    {
        try
        {
            if(orcamentoRepository.existsById(id))
            {
                OrdemServico ordemServico = ordemServicoRepository.findById(id).get();
                return new ResponseEntity<>(ordemServico, OK);
            }

        }
        catch (Exception e)
        {
            throw new Exception("erro ao listar");
        }
        return null;
    }

    public ResponseEntity<OrdemServico> BuscarOrdemServicoPorCodigo(String codigo) throws Exception
    {
        try
        {
            if(ordemServicoRepository.existsBycodigo(codigo))
            {
                OrdemServico ordemServico = ordemServicoRepository.findBycodigo(codigo);
                return new ResponseEntity<>(ordemServico, OK);
            }

        }
        catch (Exception e)
        {
            throw new Exception("erro ao listar");
        }
        return null;
    }

    public ResponseEntity<OrdemServicoDTO> NovaOrdemServico(OrdemServicoDTO dto)
     {
        try{
            if(dto != null)
            {

                if(orcamentoRepository.existsById(dto.idOrcamento()))
                {
                    Orcamento orcamento = orcamentoRepository.findById(dto.idOrcamento()).get();
                    OrdemServico ordemServico = new OrdemServico(dto);
                    ordemServico.setDataEntrada(LocalDateTime.now());
                    ordemServico.setStatusOrdenServico(StatusOrdenServico.AGUARDANDO_APROVACAO);
                    int cod = (int) (10000001 + Math.random() * 89999999);;
                    String codigo = "OS_"+cod;
                    ordemServico.setCodigo(codigo);
                    ordemServicoRepository.save(ordemServico);
                    Backup backup = new Backup();
                    backup.setAcaoBackup(SelecionarAcaoBackup.NOVA_ORDEM_SERVICO);
                    backup.setOrdemServico(ordemServico);
                    backup.setDataEvento(LocalDateTime.now());
                    backupRepository.save(backup);
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

    public ResponseEntity<OrdemServicoDTO> PedidoItemReparo(ItemReparoDTO dto)
    {
        try{
            if(dto != null)
            {
                if(ordemServicoRepository.existsById(dto.idOrdemServico()))
                {
                    OrdemServico ordemServico = ordemServicoRepository.findById(dto.idOrdemServico()).get();
                    ordemServico.setStatusOrdenServico(StatusOrdenServico.APROVADO);
                    if(fornecedorRepository.existsById(dto.idfornecedor()))
                    {
                        Fornecedor fornecedor = fornecedorRepository.findById(dto.idfornecedor()).get();
                        ItemReparo itemReparo = new ItemReparo(dto);
                        itemReparo.setFornecedor(fornecedor);
                        itemReparoRepository.save(itemReparo);
                        ordemServico.getItemsReparo().add(itemReparo);
                        ordemServicoRepository.save(ordemServico);
                        Backup backup = new Backup();
                        backup.setAcaoBackup(SelecionarAcaoBackup.ATUALIZACAO_ORDEM_SERVICO);
                        backup.setOrdemServico(ordemServico);
                        backup.setDataEvento(LocalDateTime.now());
                        backupRepository.save(backup);
                        return new ResponseEntity<>(OK);
                    }

                }

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

    public ResponseEntity<OrdemServicoDTO> FinalizarReparoOrdemServico(Long idOrdemServico, Double valorAdicional)
    {
        try{
            if(idOrdemServico != null)
            {
                if(ordemServicoRepository.existsById(idOrdemServico))
                {
                    OrdemServico ordemServico = ordemServicoRepository.findById(idOrdemServico).get();
                    ordemServico.setDataFinalizacaoReparo(LocalDateTime.now());
                    ordemServico.setStatusOrdenServico(StatusOrdenServico.AGUARDANDO_RETIRADA);
                    if(valorAdicional != null) {
                        ordemServico.setValorAdicional(valorAdicional);
                        ordemServicoRepository.save(ordemServico);
                        Backup backup = new Backup();
                        backup.setAcaoBackup(SelecionarAcaoBackup.ATUALIZACAO_ORDEM_SERVICO);
                        backup.setOrdemServico(ordemServico);
                        backup.setDataEvento(LocalDateTime.now());
                        backupRepository.save(backup);
                        return new ResponseEntity<>(OK);
                    }

                }

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

    public ResponseEntity<OrdemServicoDTO> FinalizarOrdemServico(String codigo, SelecionarPagamento selecionarPagamento)
    {
        try{
            if(codigo != null)
            {
                if(ordemServicoRepository.existsBycodigo(codigo))
                {
                    OrdemServico ordemServico = ordemServicoRepository.findBycodigo(codigo);
                    ordemServico.setDataEntrega(LocalDateTime.now());
                    ordemServico.setStatusOrdenServico(StatusOrdenServico.PRODUTO_ENTREGUE);
                    ordemServico.setSelecionarPagamento(selecionarPagamento);
                    ordemServicoRepository.save(ordemServico);
                    Backup backup = new Backup();
                    backup.setAcaoBackup(SelecionarAcaoBackup.ATUALIZACAO_ORDEM_SERVICO);
                    backup.setOrdemServico(ordemServico);
                    backup.setDataEvento(LocalDateTime.now());
                    backupRepository.save(backup);
                    return new ResponseEntity<>(OK);


                }

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

    public ResponseEntity<OrdemServicoDTO> DeletarOrdemServico(Long id) throws Exception
    {
        try
        {
            if(ordemServicoRepository.existsById(id))
            {
                ordemServicoRepository.deleteById(id);
            }
            return new ResponseEntity<>(OK);
        }
        catch (Exception e)
        {
            throw new Exception("erro ao deletar");
        }
    }

}
