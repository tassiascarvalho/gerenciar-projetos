package br.com.projetos.gerenciadorprojetos.controles;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import br.com.projetos.gerenciadorprojetos.entidades.Funcionario;
import br.com.projetos.gerenciadorprojetos.excessoes.FuncionarioPossuiProjetosException;
import br.com.projetos.gerenciadorprojetos.servicos.CargoServico;
import br.com.projetos.gerenciadorprojetos.servicos.FuncionarioServico;
import br.com.projetos.gerenciadorprojetos.validadores.FuncionarioValidador;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioControle {

    private static final String FORMULARIO_VIEW = "funcionario/formulario";
    private static final String FUNCIONARIO_ATTR = "funcionario";
    private static final String CARGOS_ATTR = "cargos";
    private static final String REDIRECT_HOME = "redirect:/funcionarios";
    private static final String ALERT_SUCCESS = "alert-success";
    private static final String ALERT = "alert";
    private static final String ALERT_DANGER = "alert-danger";
    
    private final CargoServico cargoServico;
    private final FuncionarioServico funcionarioServico;    
    private final FuncionarioValidador funcionarioValidador;

    public FuncionarioControle(CargoServico cargoServico, FuncionarioServico funcionarioServico, FuncionarioValidador funcionarioValidador) {
        this.cargoServico = cargoServico;
        this.funcionarioServico = funcionarioServico;
        this.funcionarioValidador = funcionarioValidador;
    }
    

    //Associar arquivo de validação
    @InitBinder(FUNCIONARIO_ATTR)
    private void initBinder(WebDataBinder binder){
        binder.addValidators(funcionarioValidador);        
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("funcionario/home");

        modelAndView.addObject("funcionarios", funcionarioServico.buscarTodos());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("funcionario/detalhes");

        modelAndView.addObject(FUNCIONARIO_ATTR, funcionarioServico.buscarPorID(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView(FORMULARIO_VIEW);

        modelAndView.addObject(FUNCIONARIO_ATTR, new Funcionario());
        modelAndView.addObject(CARGOS_ATTR, cargoServico.buscarTodos());
        
        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView(FORMULARIO_VIEW);

        modelAndView.addObject(FUNCIONARIO_ATTR, funcionarioServico.buscarPorID(id));
        modelAndView.addObject(CARGOS_ATTR, cargoServico.buscarTodos());
        
        return modelAndView;
    }

    @PostMapping({"/cadastrar"})
    public String cadastrar(@Valid Funcionario funcionario, BindingResult resultado, ModelMap model, RedirectAttributes attrs){
        if(resultado.hasErrors()){
            model.addAttribute(CARGOS_ATTR, cargoServico.buscarTodos());
            return FORMULARIO_VIEW;
        }
        funcionarioServico.cadastrar(funcionario);
        attrs.addFlashAttribute(ALERT, new AlertDTO("Funcionario cadastrado com Sucesso", ALERT_SUCCESS));
       
        return REDIRECT_HOME;
    }

    @PostMapping({"/{id}/editar"})
    public String editar(@Valid Funcionario funcionario, BindingResult resultado, @PathVariable Long id, ModelMap model, RedirectAttributes attrs) {
       
        //Verifica se há algum erro
        if(resultado.hasErrors()){
            model.addAttribute(CARGOS_ATTR, cargoServico.buscarTodos());
            return FORMULARIO_VIEW;
        }

        funcionarioServico.atualizar(funcionario, id);
        attrs.addFlashAttribute(ALERT, new AlertDTO("Funcionario alterado com Sucesso", ALERT_SUCCESS));
        

        return REDIRECT_HOME;
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attrs) {
        try{        
            funcionarioServico.excluirPorid(id);
            attrs.addFlashAttribute(ALERT, new AlertDTO("Funcionario excluido com Sucesso", ALERT_SUCCESS));
        }catch(FuncionarioPossuiProjetosException e){
            attrs.addFlashAttribute(ALERT, new AlertDTO("Funcionario não pode ser excluido pois é lider de projeto Controle", ALERT_DANGER));
        }
        return REDIRECT_HOME;
    }
    
}
