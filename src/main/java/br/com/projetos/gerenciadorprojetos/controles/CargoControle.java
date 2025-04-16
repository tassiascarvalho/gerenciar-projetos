package br.com.projetos.gerenciadorprojetos.controles;

import javax.validation.Valid;


import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.projetos.gerenciadorprojetos.dto.AlertDTO;
import br.com.projetos.gerenciadorprojetos.entidades.Cargo;
import br.com.projetos.gerenciadorprojetos.excessoes.CargoPossuiFuncionariosException;
import br.com.projetos.gerenciadorprojetos.servicos.CargoServico;

//As anotações também definem o formato da injeção de dependecia
//As anotações também permitem instanciar as classes especificas
//As anotações das classes beans spring definem como acontecerá a injeção de dependencia
// @Controller (Beans Spring)
// @Service - Define as classes da chamada de servico (Beans Spring)
// @Repository - São utilizados em classes responsaveis pela parte de acesso
// @Component - é utilizado para definir beans spring que não fazerm parte das camadas anteriores, classes mais genericas, geralmente utilizada em classes utils

// @Bean - Não é utilizado em classe, apenas anota métodos
// server para ensinar o spring mvc gerenciar classes das quais ele não tem conhecimento
// exemplo classes de bibliotecas externas

@Controller
@RequestMapping("/cargos")
public class CargoControle {

    //SonarQube - para não usar a mesma string varias vezes
    private static final String FORMULARIO_VIEW = "cargo/formulario";
    private static final String REDIRECT_CARGOS = "redirect:/cargos";
    private static final String ALERT_SUCCESS = "alert-success";
    private static final String ALERT = "alert";
    private static final String ALERT_DANGER = "alert-danger";
   
    // Existem varias formas de fazer injeção de dependência (Autowired, Set, Construtor)
    private final CargoServico cargoServico;

    public CargoControle(CargoServico cargoServico) {
        this.cargoServico = cargoServico;
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("cargo/home");

        modelAndView.addObject("cargos", cargoServico.buscarTodos());

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView(FORMULARIO_VIEW);

        modelAndView.addObject("cargo", new Cargo());

        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView(FORMULARIO_VIEW);

        modelAndView.addObject("cargo", cargoServico.buscarPorID(id));

        return modelAndView;
    }

    @PostMapping({ "/cadastrar", "/{id}/editar" })
    public String salvar(@Valid Cargo cargo, BindingResult resultado, RedirectAttributes attrs,
            @PathVariable(required = false) Long id) {
        // anotação @Valid é para validar de acordo com a classe, e o bindingresulte
        // retornar os erros, deve sempre vir logo apos o atributo de validação

        if (resultado.hasErrors()) {
            return FORMULARIO_VIEW;
        }

        if (cargo.getId() == null) {
            cargoServico.cadastrar(cargo);
            attrs.addFlashAttribute(ALERT, new AlertDTO("Cargo cadastrado com Sucesso", ALERT_SUCCESS));
        } else {
            cargoServico.atualizar(cargo, id);
            attrs.addFlashAttribute(ALERT, new AlertDTO("Cargo alterado com Sucesso", ALERT_SUCCESS));
        }

        return REDIRECT_CARGOS;
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attrs) {
        try {
            cargoServico.excluirPorid(id);
            attrs.addFlashAttribute(ALERT, new AlertDTO("Cargo excluido com Sucesso", ALERT_SUCCESS));
        } catch (CargoPossuiFuncionariosException e) {
            attrs.addFlashAttribute(ALERT,
                    new AlertDTO("Cargo não pode ser excluido, pois possui funcionários associados",  ALERT_DANGER));
        }

        return REDIRECT_CARGOS;
    }

}
