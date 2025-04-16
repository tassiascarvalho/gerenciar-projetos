package br.com.projetos.gerenciadorprojetos.controles;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.projetos.gerenciadorprojetos.dto.AlertDTO;
import br.com.projetos.gerenciadorprojetos.entidades.Cliente;
import br.com.projetos.gerenciadorprojetos.excessoes.ClientePossuiProjetosException;
import br.com.projetos.gerenciadorprojetos.servicos.ClienteServico;
import br.com.projetos.gerenciadorprojetos.validadores.ClienteValidador;

@Controller
@RequestMapping("/clientes")
public class ClienteControle {

    private static final String FORMULARIO_VIEW = "cliente/formulario";
    private static final String REDIRECT_CLIENTES = "redirect:/clientes";
    private static final String ALERT_SUCCESS = "alert-success";
    private static final String ALERT = "alert";
    private static final String ALERT_DANGER = "alert-danger";
    private static final String CLIENTE_ATTR = "cliente";


    private final ClienteServico clienteServico;
    private final ClienteValidador clienteValidador;

    public ClienteControle(ClienteServico clienteServico, ClienteValidador clienteValidador) {
        this.clienteServico = clienteServico;
        this.clienteValidador = clienteValidador;
    }

    // Injeção do validador no binder do Spring MVC
    @InitBinder(CLIENTE_ATTR)
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(clienteValidador);
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("cliente/home");

        modelAndView.addObject("clientes", clienteServico.buscarTodos());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cliente/detalhes");

        modelAndView.addObject(CLIENTE_ATTR, clienteServico.buscarPorID(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView(FORMULARIO_VIEW);

        modelAndView.addObject(CLIENTE_ATTR, new Cliente());
        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView(FORMULARIO_VIEW);

        modelAndView.addObject(CLIENTE_ATTR, clienteServico.buscarPorID(id));

        return modelAndView;
    }

    @PostMapping({ "/cadastrar", "/{id}/editar" })
    public String salvar(@Valid Cliente cliente, BindingResult resultado, RedirectAttributes attr,
            @PathVariable(required = false) Long id) {
        // Verifica se teve erros na validação
        if (resultado.hasErrors()) {
            // deixei o exemplo para quando há necessidade de carregar outros objetos
            // model.addAttribute("ufs", UF.values());//carregar os estados em caso de erros
            return FORMULARIO_VIEW;
        }

        // envia atributos para outras action/métodos RedirectAttributes
        if (cliente.getId() == null) {
            clienteServico.cadastrar(cliente);
            attr.addFlashAttribute(ALERT, new AlertDTO("Cliente cadastrado com Sucesso", ALERT_SUCCESS));
        } else {
            clienteServico.atualizar(cliente, id);
            attr.addFlashAttribute(ALERT, new AlertDTO("Cliente alterado com Sucesso", ALERT_SUCCESS));
        }

        return REDIRECT_CLIENTES;
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attrs) {
        try {
            clienteServico.excluirPorid(id);
            attrs.addFlashAttribute(ALERT, new AlertDTO("Cliente excluido com Sucesso", ALERT_SUCCESS));
        } catch (ClientePossuiProjetosException e) {
            attrs.addFlashAttribute(ALERT,
                    new AlertDTO("Cliente não pode ser exluído pois possui projetos associados a ele", ALERT_DANGER));
        }

        return REDIRECT_CLIENTES;
    }

}
