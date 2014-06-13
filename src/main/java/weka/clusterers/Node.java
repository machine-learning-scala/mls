/*
Based on Weka file.

  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package weka.clusterers;

import weka.core.Instances;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * class representing node in cluster hierarchy *
 */
public class Node implements Serializable {

    /**
     * ID added to avoid warning
     */
    private static final long serialVersionUID = 7639483515789717908L;

    public Node(Instances m_instances) {
        this.m_instances = m_instances;
    }

    public Node m_left;
    public Node m_right;
    public Node m_parent;
    public int m_iLeftInstance;
    public int m_iRightInstance;
    double m_fLeftLength = 0;
    double m_fRightLength = 0;
    double m_fHeight = 0;
    Instances m_instances = null;

    public String toString(int attIndex) {
        DecimalFormat myFormatter = new DecimalFormat("#.#####");

        if (m_left == null) {
            if (m_right == null) {
                return "("
                        + m_instances.instance(m_iLeftInstance).stringValue(attIndex) + ":"
                        + myFormatter.format(m_fLeftLength) + ","
                        + m_instances.instance(m_iRightInstance).stringValue(attIndex)
                        + ":" + myFormatter.format(m_fRightLength) + ")";
            } else {
                return "("
                        + m_instances.instance(m_iLeftInstance).stringValue(attIndex) + ":"
                        + myFormatter.format(m_fLeftLength) + ","
                        + m_right.toString(attIndex) + ":"
                        + myFormatter.format(m_fRightLength) + ")";
            }
        } else {
            if (m_right == null) {
                return "(" + m_left.toString(attIndex) + ":"
                        + myFormatter.format(m_fLeftLength) + ","
                        + m_instances.instance(m_iRightInstance).stringValue(attIndex)
                        + ":" + myFormatter.format(m_fRightLength) + ")";
            } else {
                return "(" + m_left.toString(attIndex) + ":"
                        + myFormatter.format(m_fLeftLength) + ","
                        + m_right.toString(attIndex) + ":"
                        + myFormatter.format(m_fRightLength) + ")";
            }
        }
    }

    public String toString2(int attIndex) {
        DecimalFormat myFormatter = new DecimalFormat("#.#####");

        if (m_left == null) {
            if (m_right == null) {
                return "(" + m_instances.instance(m_iLeftInstance).value(attIndex)
                        + ":" + myFormatter.format(m_fLeftLength) + ","
                        + m_instances.instance(m_iRightInstance).value(attIndex) + ":"
                        + myFormatter.format(m_fRightLength) + ")";
            } else {
                return "(" + m_instances.instance(m_iLeftInstance).value(attIndex)
                        + ":" + myFormatter.format(m_fLeftLength) + ","
                        + m_right.toString2(attIndex) + ":"
                        + myFormatter.format(m_fRightLength) + ")";
            }
        } else {
            if (m_right == null) {
                return "(" + m_left.toString2(attIndex) + ":"
                        + myFormatter.format(m_fLeftLength) + ","
                        + m_instances.instance(m_iRightInstance).value(attIndex) + ":"
                        + myFormatter.format(m_fRightLength) + ")";
            } else {
                return "(" + m_left.toString2(attIndex) + ":"
                        + myFormatter.format(m_fLeftLength) + ","
                        + m_right.toString2(attIndex) + ":"
                        + myFormatter.format(m_fRightLength) + ")";
            }
        }
    }

    void setHeight(double fHeight1, double fHeight2) {
        m_fHeight = fHeight1;
        if (m_left == null) {
            m_fLeftLength = fHeight1;
        } else {
            m_fLeftLength = fHeight1 - m_left.m_fHeight;
        }
        if (m_right == null) {
            m_fRightLength = fHeight2;
        } else {
            m_fRightLength = fHeight2 - m_right.m_fHeight;
        }
    }

    void setLength(double fLength1, double fLength2) {
        m_fLeftLength = fLength1;
        m_fRightLength = fLength2;
        m_fHeight = fLength1;
        if (m_left != null) {
            m_fHeight += m_left.m_fHeight;
        }
    }
}