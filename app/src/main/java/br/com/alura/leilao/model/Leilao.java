package br.com.alura.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao implements Serializable {

    private final String descricao;
    private final List<Lance> lances;
    private double maiorLance = 0.0;
    private double menorLance = 0.0;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<>();
    }

    public void propoe(Lance lance){
        if (lanceNaoValido(lance)) return;
        lances.add(lance);
        double valorLance = lance.getValor();
        if (defineMaiorEMenorLanceParaOPrimeiroLance(valorLance)) return;
        Collections.sort(lances);
        calculaMaiorLance(valorLance);
        calculaMenorLance(valorLance);
    }

    private boolean defineMaiorEMenorLanceParaOPrimeiroLance(double valorLance) {
        if(lances.size() == 1){
            maiorLance = valorLance;
            menorLance = valorLance;
            return true;
        }
        return false;
    }

    private boolean lanceNaoValido(Lance lance) {
        double valorLance = lance.getValor();
        if (lanceForMenorQueOUltimoLance(valorLance)) return true;
        if(temLances()){
            Usuario usuarioNovo = lance.getUsuario();
            if (usuarioForOMesmoDoUltimoLance(usuarioNovo)) return true;
            if (usuarioDeuCincoLances(usuarioNovo)) return true;
        }
        return false;
    }

    private boolean temLances() {
        return !lances.isEmpty();
    }

    private boolean usuarioDeuCincoLances(Usuario usuarioNovo) {
        int lancesDoUsuario = 0;
        for (Lance l :
                lances) {
            Usuario usuarioExistente = l.getUsuario();
            if(usuarioExistente.equals(usuarioNovo)){
                lancesDoUsuario++;
                if(lancesDoUsuario == 5){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean usuarioForOMesmoDoUltimoLance(Usuario usuarioNovo) {
        Usuario ultimoUsuario = lances.get(0).getUsuario();
        if(usuarioNovo.equals(ultimoUsuario)){
            return true;
        }
        return false;
    }

    private boolean lanceForMenorQueOUltimoLance(double valorLance) {
        if(maiorLance > valorLance){
            return true;
        }
        return false;
    }

    private void calculaMenorLance(double valorLance) {
        if (valorLance < menorLance){
            menorLance = valorLance;
        }
    }

    private void calculaMaiorLance(double valorLance) {
        if(valorLance > maiorLance){
            maiorLance = valorLance;
        }
    }

    public double getMenorLance() {
        return menorLance;
    }

    public double getMaiorLance() {
        return maiorLance;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Lance> tresMaioresLances() {
        int quantidadeMaximaLances = lances.size();
        if(quantidadeMaximaLances > 3){
            quantidadeMaximaLances = 3;
        }
        return lances.subList(0, quantidadeMaximaLances);
    }

    public int quantidadeLances() {
        return lances.size();
    }
}
