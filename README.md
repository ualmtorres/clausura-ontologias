# Clausura de ontologías

Repositorio con herramienta para la aplicación de clausura con ampliación y clausura con reducción en ontologías. 

* La clausura con ampliación extiende una ontología incorporando recursivamente los conceptos y relaciones semánticas de la ontología. 
* La clausura con reducción transforma la ontología original mediante una vista que elimina las referencias externas a conceptos no incorporados en la ontología original. 

Para ambas técnicas se introducen un conjunto de reglas a aplicar y se proponen los algoritmos para llevarlas a cabo. Estás técnicas de clausura de ontologías son aplicadas a ontologías OWL.

## Artículo asociado

Manuel Torres, José Samos, Eladio Garví. (2014). Closing Ontologies to Define OLAP Systems. International Journal of Information Retrieval Research (IJIRR), 4(4), 1-16. http://doi.org/10.4018/ijirr.2014100101

Ontologies can be used in the construction of OLAP (On-Line Analytical Processing) systems. In such a context, ontologies are mainly used either to enrich cube dimensions or to define ontology based-dimensions. On the one hand, if dimensions are enriched using large ontologies, like WordNet, details that are beyond the scope of the dimension may be added to it. Even, dimensions may be obscured because of the massive incorporation of related attributes. On the other hand, if ontologies are used to define a dimension, it is possible that a simplified version of the ontology is needed to define the dimension, especially when the used ontology is too complex for the dimension that is being defined. These problems may be solved using one of the existing mechanisms to define ontology views. Therefore, concepts that are not needed for the domain ontology are kept out of the view. However, this view must be closed so that, no ontology component has references to components that are not included in the view. In this work, two basic approaches are proposed: enlargement and reduction closure.
