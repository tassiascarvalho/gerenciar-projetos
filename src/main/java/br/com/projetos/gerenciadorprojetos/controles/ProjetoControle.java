package br.com.projetos.gerenciadorprojetos.controles;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.projetos.gerenciadorprojetos.dto.AlertDTO;
import br.com.projetos.gerenciadorprojetos.entidades.Projeto;
import br.com.projetos.gerenciadorprojetos.servicos.ClienteServico;
import br.com.projetos.gerenciadorprojetos.servicos.FuncionarioServico;
import br.com.projetos.gerenciadorprojetos.servicos.ProjetoServico;

@Controller
@RequestMapping("/projetos")
public class ProjetoControle {

    private static final String FORMULARIO_VIEW = "projeto/formulario";
    private static final String REDIRECT_HOME = "redirect:/projetos";
    private static final String ALERT_SUCCESS = "alert-success";
    private static final String ALERT = "alert";
    private static final String PROJETO_ATRR = "projeto";

        private final ProjetoServico projetoServico;
    private final FuncionarioServico funcionarioServico;
    private final ClienteServico clienteServico;

    public ProjetoControle(ProjetoServico projetoServico, FuncionarioServico funcionarioServico, ClienteServico clienteServico) {
        this.projetoServico = projetoServico;
        this.funcionarioServico = funcionarioServico;
        this.clienteServico = clienteServico;
    }
    
    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("projeto/home");

        modelAndView.addObject("projetos", projetoServico.buscarTodos());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projeto/detalhes");

        modelAndView.addObject(PROJETO_ATRR, projetoServico.buscarPorID(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView(FORMULARIO_VIEW);

        modelAndView.addObject(PROJETO_ATRR, new Projeto());
        popularFormulario(modelAndView);

        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView(FORMULARIO_VIEW);

        modelAndView.addObject(PROJETO_ATRR, projetoServico.buscarPorID(id));
        popularFormulario(modelAndView);
        
        return modelAndView;
    }

    @PostMapping({"/cadastrar", "/{id}/editar"})
    public String salvar(@Valid Projeto projeto, BindingResult resultado, ModelMap model, RedirectAttributes attrs, @PathVariable(required = false) Long id) {
        if(resultado.hasErrors()){
            popularFormulario(model);    
            return FORMULARIO_VIEW;
        }

        if(projeto.getId() == null){
            projetoServico.cadastrar(projeto);
            attrs.addFlashAttribute(ALERT, new AlertDTO("Projeto cadastrado com Sucesso", ALERT_SUCCESS));
        }else{
            projetoServico.atualizar(projeto, id);
            attrs.addFlashAttribute(ALERT, new AlertDTO("Projeto alterado com Sucesso", ALERT_SUCCESS));
        }

        return REDIRECT_HOME;
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attrs) {
        projetoServico.ExcluirPorId(id);
        attrs.addFlashAttribute(ALERT, new AlertDTO("Projeto Excluido com Sucesso", ALERT_SUCCESS));

        return REDIRECT_HOME;
    }


    private void popularFormulario(ModelAndView modelAndView){        
        modelAndView.addObject("clientes", clienteServico.buscarTodos());
        modelAndView.addObject("lideres", funcionarioServico.buscarLideres());
        modelAndView.addObject("funcionarios", funcionarioServico.buscarEquipe());
    }

    private void popularFormulario(ModelMap model){        
        model.addAttribute("clientes", clienteServico.buscarTodos());
        model.addAttribute("lideres",  funcionarioServico.buscarLideres());
        model.addAttribute("funcionarios", funcionarioServico.buscarEquipe());
    }
    
}
