/*
 *    Ecotype Simulation models the sequence diversity within a bacterial
 *    clade as the evolutionary result of net ecotype formation and periodic
 *    selection, yielding a certain number of ecotypes.
 *
 *    Copyright (C) 2013-2015  Jason M. Wood, Montana State University
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package ecosim;

/**
 *  An object to store the parameter values.
 *
 *  @author Jason M. Wood
 *  @copyright GNU General Public License
 */
public class ParameterSet implements Comparable<ParameterSet> {

    /**
     *  ParameterSet
     *
     *  @param omega The value of omega.
     *  @param sigma The value of sigma.
     *  @param npop The value of npop.
     *  @param likelihood Likelihood of the (npop, omega, sigma) parameter set.
     */
    public ParameterSet (Double omega, Double sigma, Long npop, Double likelihood) {
        this.omega = omega;
        this.sigma = sigma;
        this.npop = npop;
        this.likelihood = likelihood;
    }

    /**
     *  Compare the value of this parameter set with that of another.
     *
     *  @param other The other parameter set to comapre.
     *  @return 0 if the value of this parameter set is within EPSILON of
     *  being equal to the other parameter set's value. -1 if the
     *  the value is less.  1 if the value is more.
     */
    public int compareTo (ParameterSet other) {
        return likelihood.compareTo (other.getLikelihood ());
    }

    /**
     *  Get omega.
     *
     *  @return A Double containing the value of omega.
     */
    public Double getOmega () {
        return omega;
    }

    /**
     *  Get sigma.
     *
     *  @return A Double containing the value of sigma.
     */
    public Double getSigma () {
        return sigma;
    }

    /**
     *  Get npop.
     *
     *  @return The value of npop.
     */
    public Long getNpop () {
        return npop;
    }

    /**
     *  Get the likelihood.
     *
     *  @return The likelihood.
     */
    public Double getLikelihood () {
        return likelihood;
    }

    /**
     *  Returns the parameter set as a string, in the form:
     *  omega, sigma, npop, value
     *
     *  @return String containing the values of omega, sigma, and npop, and
     *  the likelihood of those values.
     */
    public String toString () {
        return String.format (
            "  omega:       %.4g\n" +
            "  sigma:       %.4g\n" +
            "  npop:        %d\n" +
            "  likelihood:  %.4g", omega, sigma, npop, likelihood
        );
    }

    private Double omega;
    private Double sigma;
    private Long npop;
    private Double likelihood;

}
